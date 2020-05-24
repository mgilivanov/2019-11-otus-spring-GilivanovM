package ru.mgilivanov.project.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgilivanov.project.model.*;
import ru.mgilivanov.project.model.credit.*;
import ru.mgilivanov.project.service.CreditService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreditController {
    private final CreditService creditService;

    public static final String API_PREFIX = "/api";
    public static final String CREDIT_PREFIX = API_PREFIX + "/credit";
    public static final String CREDIT_ISSUE = CREDIT_PREFIX + "/issue";
    public static final String CREDIT_INFO = CREDIT_PREFIX + "/info";
    public static final String CREDITS_FOR_CLIENT = CREDIT_PREFIX + "/info_by_client";
    public static final String CREDITS_BY_DAY = CREDIT_PREFIX + "/reestr_by_day";
    public static final String CREDIT_PREPAYMENT = CREDIT_PREFIX + "/prepayment";
    public static final String CREDIT_EOD_ERRORS = CREDIT_PREFIX + "/eod_errors";

    @ApiOperation("Выдача кредита")
    @PostMapping(CREDIT_ISSUE)
    public CreditInfoResponse issue(@NotNull @Validated @RequestBody CreditIssueRequest request) {
        return new CreditInfoResponse(creditService.issue(request));
    }

    @ApiOperation("Информация по кредиту")
    @PostMapping(CREDIT_INFO)
    public CreditInfoResponse info(@NotNull @Validated @RequestBody CreditInfoRequest request) {
        return new CreditInfoResponse(creditService.info(request));
    }

    @ApiOperation("Информация по кредитам клиента")
    @PostMapping(CREDITS_FOR_CLIENT)
    public CreditForClientResponse infoForClient(@NotNull @Validated @RequestBody CreditForClientRequest request) {
        return new CreditForClientResponse(creditService.findAllByClientId(request.getClientId()));
    }

    @ApiOperation("Информация по новым кредитам за день")
    @PostMapping(CREDITS_BY_DAY)
    public CreditForClientResponse reestr(@NotNull @Validated @RequestBody CreditReestrRequest request) {
        return new CreditForClientResponse(creditService.findAllByIssueDate(request.getIssueDate()));
    }

    @ApiOperation("Список договоров с ошибками в закрытии дня")
    @PostMapping(CREDIT_EOD_ERRORS)
    public Result create(@NotNull @Validated @RequestBody CreditEodFailedRequest request) {
        return new CreditEodFailedResponse(creditService.findFailedEodCredits());
    }

}
