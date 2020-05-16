package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Eod;
import ru.mgilivanov.project.integration.CloseDayGateway;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloseDayServiceImpl implements CloseDayService {
    private final CreditService creditService;
    private final PayDocumentService payDocumentService;
    private final EodService eodService;
    private final CloseDayGateway closeDayGateway;

    @Override
    @Async
    public void runEod(LocalDate date) throws InterruptedException {
        Eod eod = eodService.getCurrentEod();
        LocalDate newEodDate = eodService.openNewEod(date);
        List<Credit> credits = creditService.findAllByStatus(Credit.STATUS_ACTIVE);
        Thread.sleep(10000);
        for (Credit credit : credits){
            try {
                creditService.runEodForCredit(credit, date);
                creditService.setLastProcessedMessage(credit, "OK");
            }
            catch (RuntimeException exception){
                creditService.setLastProcessedMessage(credit, exception.getMessage());
            }
        }
        eodService.closeOldEod();
        payDocumentService.processAllValueDateDocs(newEodDate);
    }

}
