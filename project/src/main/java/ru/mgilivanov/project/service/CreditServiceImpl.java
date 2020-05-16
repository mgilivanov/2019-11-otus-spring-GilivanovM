package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.*;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;
import ru.mgilivanov.project.model.credit.CreditPrepaymentRequest;
import ru.mgilivanov.project.repository.CreditRepository;
import ru.mgilivanov.project.repository.PrepaymentRepository;
import ru.mgilivanov.project.utils.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final ClientService clientService;
    private final AccountService accountService;
    private final CreditRepository creditRepository;
    private final PrepaymentRepository prepaymentRepository;
    private final PayDocumentService payDocumentService;
    private final EodService eodService;

    @Override
    public Optional<Credit> findById(long id) {
        return creditRepository.findById(id);
    }

    @Override
    public List<Credit> findAllByStatus(String status){
        return creditRepository.findAllByStatus(status);
    }

    @Override
    public Credit issue(CreditIssueRequest request){
        Optional<Client> optClient = clientService.findById(request.getClientId());
        if (optClient.isEmpty()){
            throw new BusinessLogicException(BusinessLogicException.CLIENT_NOT_FOUND_CODE, BusinessLogicException.CLIENT_NOT_FOUND_MESSAGE);
        }
        Credit credit = new Credit()
                .setClient(optClient.get())
                .setIssueDate(eodService.getEodDate())
                .setNextStmtDate(request.getFirstStmtDate())
                .setPercentRate(request.getPercentRate())
                .setRegPayment(request.getRegPayment())
                .setStatus(Credit.Status.ACTIVE)
                .setStmtDay(request.getFirstStmtDate().getDayOfMonth())
                .setSum(request.getSum())
                .setApplicationId(request.getApplicationId())
                .setOverdueFee(request.getOverdueFee());
        creditRepository.save(credit);

        List<Account> accounts = accountService.openCreditAccounts(credit);
        credit.setAccounts(accounts);

        Map<String, Account> accountsMap = accountService.toMap(accounts);

        payDocumentService.create(PayDocument.Type.ISSUE,
                eodService.getEodDate(),
                accountsMap.get(AccountType.USUAL_DEBT),
                accountsMap.get(AccountType.SERVICE),
                credit.getSum());
        return credit;
    }

    @Override
    public List<Credit> findAllByClientId(long clientId) {
        return creditRepository.findAllByClientId(clientId);
    }

    @Override
    public Credit info(CreditInfoRequest request) {
        return findById(request.getCreditId())
                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.CREDIT_NOT_FOUND_CODE, BusinessLogicException.CREDIT_NOT_FOUND_MESSAGE));
    }

    @Override
    public void repaymentOverDue(Credit credit) {
        Double overdueAmount = accountService.getGroupDebtOverdue(credit.getAccounts());
        if (overdueAmount > 0){
            repayment(credit, overdueAmount, PayDocument.Type.REPAYMENT_OVERDUE);
        }
    }

    @Override
    public void repaymentPenalty(Credit credit) {
        Double penaltyAmount = accountService.getGroupDebtPenalty(credit.getAccounts());
        if (penaltyAmount > 0){
            repayment(credit, penaltyAmount, PayDocument.Type.REPAYMENT_PENALTY);
        }
    }

    @Override
    public void regularPayment(Credit credit) {
        Double regularAmount = accountService.getGroupDebtRegular(credit.getAccounts());
        if (regularAmount > 0) {
            repayment(credit, regularAmount, PayDocument.Type.REPAYMENT_REGULAR);
        }
    }

    @Override
    public void repayment(Credit credit, Double sum, String type){
        Account serviceAccount = accountService.toMap(credit.getAccounts()).get(AccountType.SERVICE);
        Double balanceLeft  = serviceAccount.getBalance();
        if ((balanceLeft <= 0) || (sum == 0)) return;
        Double sumLeft = sum;
        List<Account> creditAccounts = credit.getAccounts().stream()
                .filter(i -> i.getAccountType().getRepaymentOrder() != null)
                .sorted(Comparator.comparingInt(i -> i.getAccountType().getRepaymentOrder()))
                .collect(Collectors.toList());
        for (Account account : creditAccounts){
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
        };
    }

    @Override
    public void capitalizationInterest(Credit credit) {
        Map <String, Account> accountsMap = accountService.toMap(credit.getAccounts());
        payDocumentService.create(PayDocument.Type.CAPITALIZATION_PERCENTS,
                eodService.getClosingEodDate(),
                accountsMap.get(AccountType.USUAL_PERCENTS),
                accountsMap.get(AccountType.UNAPPLIED_PERCENTS),
                Precision.round((-1) * accountsMap.get(AccountType.UNAPPLIED_PERCENTS).getBalance(), 2, 3));
    }

    @Override
    public void allotmentPayment(Credit credit) {
        Map <String, Account> accountsMap = accountService.toMap(credit.getAccounts());

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

    @Override
    public void transferToOverdue(Credit credit) {
        Map <String, Account> accountsMap = accountService.toMap(credit.getAccounts());

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

    @Override
    public void chargeFines(Credit credit) {
       Double overdueAmount = accountService.getGroupDebtOverdue(credit.getAccounts());
       if (overdueAmount < 0) {
           Map <String, Account> accountsMap = accountService.toMap(credit.getAccounts());
           payDocumentService.create(PayDocument.Type.CHARGE_FINE,
                   eodService.getClosingEodDate(),
                   accountsMap.get(AccountType.PENALTY),
                   accountService.getBankAccountPenalty(),
                   credit.getOverdueFee());
       }
    }

    @Override
    public void accrualInterest(Credit credit) {
        Map <String, Account> accountsMap = accountService.toMap(credit.getAccounts());

        Double sum = (-1) * accountsMap.get(AccountType.USUAL_DEBT).getBalance()
                * credit.getPercentRate()
                / (100 * (DateUtil.getDaysInYear(eodService.getEodDate())));

        payDocumentService.create(PayDocument.Type.ACCRUAL_INTEREST,
                eodService.getEodDate(),
                accountsMap.get(AccountType.UNAPPLIED_PERCENTS),
                accountService.getBankAccountPercents(),
                sum
        );
    }

    @Override
    public void closeStatement(Credit credit){
        credit.setNextStmtDate(DateUtil.getNextPeriod(credit.getStmtDay(), credit.getNextStmtDate()));
        creditRepository.save(credit);
    }

    @Override
    public void closeWithoutDebt(Credit credit){
        if (accountService.getFullDebt(credit.getAccounts()) == 0){
            credit.setStatus(Credit.STATUS_CLOSED);
            creditRepository.save(credit);
        }
    }

    @Override
    public void setLastProcessedDate(Credit credit) {
        credit.setLastProcessedDate(eodService.getClosingEodDate());
        creditRepository.save(credit);
    }


    @Override
    public Prepayment createPrepayment(CreditPrepaymentRequest request) {
        request.validate();
        if (request.getDate().isBefore(eodService.getEodDate())){
            throw new BusinessLogicException(BusinessLogicException.PREPAYMENT_BEFORE_EODDATE_CODE, BusinessLogicException.PREPAYMENT_BEFORE_EODDATE_MESSAGE);
        }
        return prepaymentRepository.save(
                new Prepayment()
                        .setCredit(findById(request.getCreditId())
                                .orElseThrow(() -> new BusinessLogicException(BusinessLogicException.CREDIT_NOT_FOUND_CODE, BusinessLogicException.CREDIT_NOT_FOUND_MESSAGE)))
                        .setDate(request.getDate())
                        .setFull(request.getIsFull())
                        .setSum(request.getSum())
                        .setCreateDate(LocalDateTime.now())
                        .setStatus(Prepayment.Status.NEW));
    }

    @Override
    public List<Prepayment> findPrepayments(Credit credit, LocalDate date, String status) {
        return prepaymentRepository.findAllByCreditAndDateAndStatus(credit, date, status);
    }

    @Override
    public void applyAllPrepayments(LocalDate date){
        List<Prepayment> applications = prepaymentRepository.findAllByDateAndStatusOrderById(eodService.getClosingEodDate(), Prepayment.Status.NEW);
        for (Prepayment application : applications){
            Double balance  = accountService.toMap(application.getCredit().getAccounts()).get(AccountType.SERVICE).getBalance();
            if (balance < application.getSum()){
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void runEodForCredit(Credit credit, LocalDate date){
        repaymentOverDue(credit);
        chargeFines(credit);
        if (credit.getNextStmtDate().equals(date)){
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

    @Override
    public List<Credit> failedEodCredits(){
        if (eodService.isDayClosing()){
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_DAY_CLOSING_CODE, BusinessLogicException.DOCUMENT_DAY_CLOSING_MESSAGE);
        }
        return creditRepository.findAllByLastProcessedDateBeforeAndIssueDateBefore(eodService.getPrevEodDate(), eodService.getEodDate());
    }

    @Override
    public void setLastProcessedMessage(Credit credit, String message){
        credit.setLastProcessedMessage(message.substring(1, 32000));
        creditRepository.save(credit);
    }

    public List<Credit> findAllByIssueDate(LocalDate issueDate){
        return creditRepository.findAllByIssueDate(issueDate);
    }

}
