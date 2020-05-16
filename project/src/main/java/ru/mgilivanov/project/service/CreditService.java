package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Prepayment;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;
import ru.mgilivanov.project.model.credit.CreditPrepaymentRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditService {

    Optional<Credit> findById(long id);

    List<Credit> findAllByStatus(String status);

    Credit issue(CreditIssueRequest request);

    List<Credit> findAllByClientId(long clientId);

    List<Credit> findAllByIssueDate(LocalDate issueDate);

    Credit info(CreditInfoRequest request);

    void capitalizationInterest(Credit credit);

    void repayment(Credit credit, Double sum, String type);

    void repaymentPenalty(Credit credit);

    void repaymentOverDue(Credit credit);

    void allotmentPayment(Credit credit);

    void regularPayment(Credit credit);

    void transferToOverdue(Credit credit);

    void chargeFines(Credit credit);

    void accrualInterest(Credit credit);

    void closeStatement(Credit credit);

    void closeWithoutDebt(Credit credit);

    void setLastProcessedDate(Credit credit);

    Prepayment createPrepayment(CreditPrepaymentRequest request);

    List<Prepayment> findPrepayments(Credit credit, LocalDate date, String status);

    void applyAllPrepayments(LocalDate date);

    void runEodForCredit(Credit credit, LocalDate date);

    List<Credit> failedEodCredits();

    void setLastProcessedMessage(Credit credit, String message);

}
