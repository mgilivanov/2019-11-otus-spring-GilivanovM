package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Credit;

import java.time.LocalDate;

public interface CloseDayCreditService {

    void runEodForCredit(Credit credit, LocalDate date);

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

    void applyAllPrepayments(LocalDate date);

    void setLastProcessedMessage(Credit credit, String message);
}
