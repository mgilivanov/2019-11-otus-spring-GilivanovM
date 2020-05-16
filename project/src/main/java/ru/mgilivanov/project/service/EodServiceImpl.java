package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mgilivanov.project.domain.Eod;
import ru.mgilivanov.project.exception.ApplicationException;
import ru.mgilivanov.project.exception.BusinessLogicException;
import ru.mgilivanov.project.integration.CloseDayGateway;
import ru.mgilivanov.project.model.eod.EodStateRequest;
import ru.mgilivanov.project.model.eod.EodStateResponse;
import ru.mgilivanov.project.repository.EodRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EodServiceImpl implements EodService {
    private final EodRepository eodRepository;
    private final CloseDayGateway closeDayGateway;

    @Override
    public Eod getCurrentEod() {
        List<Eod> eods = eodRepository.findByStatus(Eod.Status.OPEN);
        if (eods.size() > 1)
            throw new ApplicationException(ApplicationException.TOO_MANY_EODS_CODE, ApplicationException.TOO_MANY_EODS_MESSAGE);
        if (eods.size() == 0)
            throw new ApplicationException(ApplicationException.NO_EODS_CODE, ApplicationException.NO_EODS_MESSAGE);
        return eods.get(0);
    }

    @Override
    public Eod getClosingEod() {
        List<Eod> eods = eodRepository.findByStatus(Eod.Status.CLOSING);
        if (eods.size() > 1)
            throw new ApplicationException(ApplicationException.TOO_MANY_CLOSING_EODS_CODE, ApplicationException.TOO_MANY_CLOSING_EODS_MESSAGE);
        if (eods.size() == 0)
            throw new ApplicationException(ApplicationException.NO_CLOSING_EODS_CODE, ApplicationException.NO_CLOSING_EODS_MESSAGE);
        return eods.get(0);
    }

    @Override
    public LocalDate getClosingEodDate() {
        return getClosingEod().getDate();
    }

    @Override
    public LocalDate getEodDate() {
        return getCurrentEod().getDate();
    }

    @Override
    public LocalDate getPrevEodDate(){
        return getEodDate().plusDays(-1);
    }

    @Override
    public boolean isDayClosing(){
        return !eodRepository.findFirstByStatus(Eod.Status.CLOSING).isEmpty();
    }

    @Transactional
    @Override
    public LocalDate openNewEod(LocalDate currentEodDate){
        if (isDayClosing()){
            throw new BusinessLogicException(BusinessLogicException.EOD_IS_ALREADY_CLOSING_CODE, BusinessLogicException.EOD_IS_ALREADY_CLOSING_MESSAGE);
        }
        if (!currentEodDate.equals(getEodDate())){
            throw new BusinessLogicException(BusinessLogicException.EOD_NOT_THIS_CODE, BusinessLogicException.EOD_NOT_THIS_MESSAGE);
        }
        closeDayGateway.processCloseDay(getCurrentEod().setCloseTimeStart(LocalDateTime.now()).setStatus(Eod.Status.CLOSING), false);
        Eod newEod = new Eod().setDate(currentEodDate.plusDays(1)).setOpenTime(LocalDateTime.now()).setStatus(Eod.Status.OPEN);
        eodRepository.save(newEod);
        return newEod.getDate();
    }

    @Transactional
    @Override
    public void closeOldEod(){
        Eod eod = getClosingEod().setCloseTimeEnd(LocalDateTime.now()).setStatus(Eod.Status.CLOSED);
        closeDayGateway.processCloseDay(eod, true);
    }

    @Override
    public EodStateResponse status(){
        Eod closingEod = eodRepository.findFirstByStatus(Eod.Status.CLOSING).orElse(null);
        Eod currentEod = getCurrentEod();
        EodStateResponse eodStateResponse = new EodStateResponse()
                .setStatus(currentEod.getStatus())
                .setOpenDate(currentEod.getDate());
        if (closingEod != null){
            eodStateResponse.setClosingDate(closingEod.getDate())
                    .setStartClosingTime(closingEod.getCloseTimeStart())
                    .setStatus(closingEod.getStatus());
        }
        return eodStateResponse;
    }

}
