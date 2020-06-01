package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.model.credit.CreditInfoRequest;
import ru.mgilivanov.project.model.credit.CreditIssueRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditService {

    Optional<Credit> findById(long id);

    List<Credit> findAllByStatus(String status);

    List<Credit> findAllByClientId(long clientId);

    List<Credit> findAllByIssueDate(LocalDate issueDate);

    Credit issue(CreditIssueRequest request);

    Credit info(CreditInfoRequest request);

    List<Credit> findFailedEodCredits();



}
