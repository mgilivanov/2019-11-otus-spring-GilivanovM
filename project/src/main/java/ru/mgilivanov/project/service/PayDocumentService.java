package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Account;
import ru.mgilivanov.project.domain.PayDocument;
import ru.mgilivanov.project.model.paydoc.PayDocumentCreateRequest;

import java.time.LocalDate;
import java.util.List;

public interface PayDocumentService {
    PayDocument create(String type, LocalDate eodDate, Account accountDt, Account accountKt, Double sum);
    PayDocument create(PayDocumentCreateRequest request);
    PayDocument process(PayDocument payDocument);
    void processAllValueDateDocs(LocalDate date);
    List<PayDocument> findAllByEodDate(LocalDate date);
    long countByEodDate(LocalDate date);
}
