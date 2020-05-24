package ru.mgilivanov.project.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgilivanov.project.model.Result;
import ru.mgilivanov.project.model.credit.CreditPrepaymentRequest;
import ru.mgilivanov.project.service.CreditPrepaymentService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrepaymentController {
    private final CreditPrepaymentService creditPrepaymentService;

    public static final String API_PREFIX = "/api";
    public static final String CREDIT_PREFIX = API_PREFIX + "/prepayment";
    public static final String CREDIT_PREPAYMENT = CREDIT_PREFIX + "/add";

    @ApiOperation("Создание заявки на частичное/полное досрочное погашение кредита")
    @PostMapping(CREDIT_PREPAYMENT)
    public Result create(@NotNull @Validated @RequestBody CreditPrepaymentRequest request) {
        return new Result(creditPrepaymentService.createPrepayment(request));
    }
}
