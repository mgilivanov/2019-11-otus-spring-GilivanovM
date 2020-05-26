package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.*;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;
import ru.mgilivanov.project.repository.CreditRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final ClientService clientService;
    private final AccountService accountService;
    private final CreditRepository creditRepository;
    private final PayDocumentService payDocumentService;
    private final EodService eodService;

    @Override
    public Optional<Credit> findById(long id) {
        return creditRepository.findById(id);
    }

    @Override
    public List<Credit> findAllByStatus(String status) {
        return creditRepository.findAllByStatus(status);
    }

    @Override
    public Credit issue(CreditIssueRequest request) {
        Optional<Client> optClient = clientService.findById(request.getClientId());
        if (optClient.isEmpty()) {
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
    public List<Credit> findAllByIssueDate(LocalDate issueDate) {
        return creditRepository.findAllByIssueDate(issueDate);
    }

    @Override
    public List<Credit> findFailedEodCredits() {
        if (eodService.isDayClosing()) {
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_DAY_CLOSING_CODE, BusinessLogicException.DOCUMENT_DAY_CLOSING_MESSAGE);
        }
        return creditRepository.findAllByLastProcessedDateBeforeAndIssueDateBefore(eodService.getPrevEodDate(), eodService.getEodDate());
    }

}
