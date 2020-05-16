package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.config.AppProps;
import ru.mgilivanov.project.domain.Eod;

@RequiredArgsConstructor
@Service
public class EodToEmailTransformerImpl implements EodToEmailTransformer {

    private final AppProps appProps;

    @Override
    public SimpleMailMessage transformStart(Eod eod) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(appProps.getAdminEmail());
        mailMessage.setFrom(appProps.getServerEmail());
        mailMessage.setSubject(String.format("Запущено закрытие дня %s", eod.getDate()));
        mailMessage.setText(String.format("Запущено закрытие дня %s. Время запуска: %s",
                eod.getDate(), eod.getCloseTimeStart()));
        return mailMessage;
    }

    @Override
    public SimpleMailMessage transformEnd(Eod eod) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(appProps.getAdminEmail());
        mailMessage.setFrom(appProps.getServerEmail());
        mailMessage.setSubject(String.format("Закрытие дня %s", eod.getDate()));
        mailMessage.setText(String.format("Завершено закрытие дня %s. Время запуска: %s, время завершения: %s",
                eod.getDate(), eod.getCloseTimeStart(), eod.getCloseTimeEnd()));
        return mailMessage;
    }
}
