package ru.mgilivanov.project.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgilivanov.project.model.eod.CloseDayRequest;
import ru.mgilivanov.project.model.Result;
import ru.mgilivanov.project.model.eod.EodStateRequest;
import ru.mgilivanov.project.model.eod.EodStateResponse;
import ru.mgilivanov.project.service.CloseDayService;
import ru.mgilivanov.project.service.EodService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CloseDayController {
    private final CloseDayService closeDayService;
    private final EodService eodService;
    public static final String API_PREFIX = "/api";
    public static final String EOD_PREFIX = API_PREFIX + "/eod";
    public static final String CLOSE_DAY_RUN = EOD_PREFIX + "/close";
    public static final String EOD_STATUS = EOD_PREFIX + "/status";

    @ApiOperation("Закрытие опер.дня")
    @PostMapping(CLOSE_DAY_RUN)
    public Result save(@NotNull @Validated @RequestBody CloseDayRequest request) throws InterruptedException {
        closeDayService.prepareRunEod(request.getDate());
        closeDayService.runEod(request.getDate());
        return new Result();
    }

    @ApiOperation("Состояние операционного дня")
    @PostMapping(EOD_STATUS)
    public EodStateResponse status(@NotNull @Validated @RequestBody EodStateRequest request) {
        return eodService.status();
    }
}
