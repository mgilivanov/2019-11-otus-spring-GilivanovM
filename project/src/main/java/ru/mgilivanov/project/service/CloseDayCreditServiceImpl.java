package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.*;
import ru.mgilivanov.project.repository.CreditRepository;
import ru.mgilivanov.project.repository.PrepaymentRepository;
import ru.mgilivanov.project.utils.DateUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CloseDayCreditServiceImpl implements CloseDayCreditService {
    private final AccountService accountService;
    private final CreditRepository creditRepository;
    private final PrepaymentRepository prepaymentRepository;

    private final PayDocumentService payDocumentService;
    private final EodService eodService;
    private final CreditService creditService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void runEodForCredit(Credit credit, LocalDate date) {
        repaymentOverDue(credit);
        chargeFines(credit);
        if (credit.getNextStmtDate().equals(date)) {
            capitalizationInterest(credit);
            allotmentPayment(credit);
            regularPayment(credit);
            transferToOverdue(credit);
            chargeFines(credit);
            closeStatement(credit);
        }
        repaymentPenalty(credit);
        closeWithoutDebt(credit);
        applyAllPrepayments(date);
        accrualInterest(credit);
        setLastProcessedDate(credit);
    }

    private void repaymentOverDue(Credit credit) {
        Double overdueAmount = accountService.getGroupDebtOverdue(credit.getAccounts());
        if (overdueAmount > 0) {
            repayment(credit, overdueAmount, PayDocument.Type.REPAYMENT_OVERDUE);
        }
    }

    private void repaymentPenalty(Credit credit) {
        Double penaltyAmount = accountService.getGroupDebtPenalty(credit.getAccounts());
        if (penaltyAmount > 0) {
            repayment(credit, penaltyAmount, PayDocument.Type.REPAYMENT_PENALTY);
        }
    }

    private void regularPayment(Credit credit) {
        Double regularAmount = accountService.getGroupDebtRegular(credit.getAccounts());
        if (regularAmount > 0) {
            repayment(credit, regularAmount, PayDocument.Type.REPAYMENT_REGULAR);
        }
    }

    private void repayment(Credit credit, Double sum, String type) {
        Account serviceAccount = accountService.toMap(credit.getAccounts()).get(AccountType.SERVICE);
        Double balanceLeft = serviceAccount.getBalance();
        if ((balanceLeft <= 0) || (sum == 0)) return;
        Double sumLeft = sum;
        List<Account> creditAccounts = credit.getAccounts().stream()
                .filter(i -> i.getAccountType().getRepaymentOrder() != null)
                .sorted(Comparator.comparingInt(i -> i.getAccountType().getRepaymentOrder()))
                .collect(Collectors.toList());
        for (Account account : creditAccounts) {
            Double sumDoc = Math.min(balanceLeft, Math.min(sumLeft, (-1) * account.getBalance()));
            if (sumDoc > 0) {
                payDocumentService.create(type,
                        eodService.getClosingEodDate(),
                        serviceAccount,
                        account,
                        sumDoc);
                balanceLeft -= sumDoc;
                sumLeft -= sumDoc;
                if ((balanceLeft == 0) || (sumLeft == 0)) return;
            }
        }
    }

    private void capitalizationInterest(Credit credit) {
        Map<String, Account> accountsMap = accountService.toMap(credit.getAccounts());
        payDocumentService.create(PayDocument.Type.CAPITALIZATION_PERCENTS,
                eodService.getClosingEodDate(),
                accountsMap.get(AccountType.USUAL_PERCENTS),
                accountsMap.get(AccountType.UNAPPLIED_PERCENTS),
                Precision.round((-1) * accountsMap.get(AccountType.UNAPPLIED_PERCENTS).getBalance(), 2, 3));
    }

    private void allotmentPayment(Credit credit) {
        Map<String, Account> accountsMap = accountService.toMap(credit.getAccounts());

        Double remainSum = credit.getRegPayment();
        Double sumPercents = Math.min(remainSum, (-1) * accountsMap.get(AccountType.USUAL_PERCENTS).getBalance());
        remainSum = remainSum - sumPercents;
        Double sumDebt = Math.min(remainSum, (-1) * accountsMap.get(AccountType.USUAL_DEBT).getBalance());

        payDocumentService.create(PayDocument.Type.ALLOTMENT_PAYMENT,
                eodService.getClosingEodDate(),
                accountsMap.get(AccountType.PAYMENT_PERCENTS),
                accountsMap.get(AccountType.USUAL_PERCENTS),
                sumPercents
        );
        payDocumentService.create(PayDocument.Type.ALLOTMENT_PAYMENT,
                eodService.getClosingEodDate(),
                accountsMap.get(AccountType.PAYMENT_DEBT),
                accountsMap.get(AccountType.USUAL_DEBT),
                sumDebt);
    }

    private void transferToOverdue(Credit credit) {
        Map<String, Account> accountsMap = accountService.toMap(credit.getAccounts());

        Double percentsToOverdue = (-1) * accountsMap.get(AccountType.PAYMENT_PERCENTS).getBalance();
        if (percentsToOverdue > 0) {
            payDocumentService.create(PayDocument.Type.TRANSFER_TO_OVERDUE,
                    eodService.getClosingEodDate(),
                    accountsMap.get(AccountType.OVERDUE_PERCENTS),
                    accountsMap.get(AccountType.PAYMENT_PERCENTS),
                    percentsToOverdue
            );
        }

        Double debtToOverdue = (-1) * accountsMap.get(AccountType.PAYMENT_DEBT).getBalance();
        payDocumentService.create(PayDocument.Type.TRANSFER_TO_OVERDUE,
                eodService.getClosingEodDate(),
                accountsMap.get(AccountType.OVERDUE_DEBT),
                accountsMap.get(AccountType.PAYMENT_DEBT),
                debtToOverdue);
    }

    private void chargeFines(Credit credit) {
        Double overdueAmount = accountService.getGroupDebtOverdue(credit.getAccounts());
        if (overdueAmount < 0) {
            Map<String, Account> accountsMap = accountService.toMap(credit.getAccounts());
            payDocumentService.create(PayDocument.Type.CHARGE_FINE,
                    eodService.getClosingEodDate(),
                    accountsMap.get(AccountType.PENALTY),
                    null,
                    credit.getOverdueFee());
        }
    }

    private void accrualInterest(Credit credit) {
        Map<String, Account> accountsMap = accountService.toMap(credit.getAccounts());

        Double sum = (-1) * accountsMap.get(AccountType.USUAL_DEBT).getBalance()
                * credit.getPercentRate()
                / (100 * (DateUtil.getDaysInYear(eodService.getEodDate())));

        payDocumentService.create(PayDocument.Type.ACCRUAL_INTEREST,
                eodService.getEodDate(),
                accountsMap.get(AccountType.UNAPPLIED_PERCENTS),
                null,
                sum
        );
    }

    private void closeStatement(Credit credit) {
        credit.setNextStmtDate(DateUtil.getNextPeriod(credit.getStmtDay(), credit.getNextStmtDate()));
        creditRepository.save(credit);
    }

    private void closeWithoutDebt(Credit credit) {
        if (accountService.getFullDebt(credit.getAccounts()) == 0) {
            credit.setStatus(Credit.STATUS_CLOSED);
            creditRepository.save(credit);
        }
    }

    private void setLastProcessedDate(Credit credit) {
        credit.setLastProcessedDate(eodService.getClosingEodDate());
        creditRepository.save(credit);
    }

    private void applyAllPrepayments(LocalDate date) {
        List<Prepayment> applications = prepaymentRepository.findAllByDateAndStatusOrderById(eodService.getClosingEodDate(), Prepayment.Status.NEW);
        for (Prepayment application : applications) {
            Double balance = accountService.toMap(application.getCredit().getAccounts()).get(AccountType.SERVICE).getBalance();
            if (balance < application.getSum()) {
                application.setStatus(Prepayment.Status.REJECTED);
                prepaymentRepository.save(application);
                return;
            } else {
                capitalizationInterest(application.getCredit());
                repayment(application.getCredit(), application.getSum(), PayDocument.Type.REPAYMENT_PREPAYMENT);
                application.setStatus(Prepayment.Status.COMPLETED);
                prepaymentRepository.save(application);
            }
        }
    }

    @Override
    public void setLastProcessedMessage(Credit credit, String message) {
        if (message == null) {
            credit.setLastProcessedMessage(null);
        } else {
            credit.setLastProcessedMessage(message.substring(0, Integer.min(message.length(), 32000)));
        }
        creditRepository.save(credit);
    }
}
