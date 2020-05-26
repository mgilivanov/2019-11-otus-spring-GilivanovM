package ru.mgilivanov.project.service;

import java.time.LocalDate;

public interface CloseDayService {

    void runEod(LocalDate date) throws InterruptedException;

    void prepareRunEod(LocalDate date);
}
