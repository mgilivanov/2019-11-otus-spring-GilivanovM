package ru.mgilivanov.project.service;

import org.springframework.mail.SimpleMailMessage;
import ru.mgilivanov.project.domain.Eod;

public interface EodToEmailTransformer {
    SimpleMailMessage transformStart(Eod eod);
    SimpleMailMessage transformEnd(Eod eod);
}
