package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Eod;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloseDayServiceImpl implements CloseDayService {
    private final CloseDayCreditService closeDayCreditService;
    private final CreditService creditService;
    private final PayDocumentService payDocumentService;
    private final EodService eodService;

    @Override
    public void prepareRunEod(LocalDate date){
        eodService.getCurrentEod();
        eodService.openNewEod(date);
    }

    @Override
    @Async
    @Transactional
    public void runEod(LocalDate date) throws InterruptedException {
        Eod eod = eodService.getCurrentEod();
        List<Credit> credits = creditService.findAllByStatus(Credit.STATUS_ACTIVE);
        Thread.sleep(10000);
        for (Credit credit : credits){
            try {
                closeDayCreditService.runEodForCredit(credit, date);
                closeDayCreditService.setLastProcessedMessage(credit, "OK");
            }
            catch (Exception exception){
                closeDayCreditService.setLastProcessedMessage(credit, exception.getMessage());
            }
        }
        eodService.closeOldEod();
        payDocumentService.processAllValueDateDocs(eod.getDate());
    }

}
