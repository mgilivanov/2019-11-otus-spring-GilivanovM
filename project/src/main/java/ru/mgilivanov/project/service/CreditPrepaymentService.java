package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Prepayment;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;
import ru.mgilivanov.project.model.credit.CreditPrepaymentRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditPrepaymentService {

    Prepayment createPrepayment(CreditPrepaymentRequest request);

    List<Prepayment> findPrepayments(Credit credit, LocalDate date, String status);
}
