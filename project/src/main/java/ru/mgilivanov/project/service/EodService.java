package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Eod;
import ru.mgilivanov.project.model.eod.EodStateResponse;

import java.time.LocalDate;

public interface EodService {
    Eod getCurrentEod();
    Eod getClosingEod();
    LocalDate getClosingEodDate();
    LocalDate getEodDate();
    LocalDate getPrevEodDate();
    boolean isDayClosing();
    LocalDate openNewEod(LocalDate currentEodDate);
    void closeOldEod();
    EodStateResponse status();
}
