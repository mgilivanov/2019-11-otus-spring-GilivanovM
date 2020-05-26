package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Credit;

import java.time.LocalDate;

public interface CloseDayCreditService {

    void runEodForCredit(Credit credit, LocalDate date);

    void setLastProcessedMessage(Credit credit, String message);
}
