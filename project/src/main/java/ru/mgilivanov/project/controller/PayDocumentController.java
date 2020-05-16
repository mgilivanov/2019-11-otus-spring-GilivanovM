package ru.mgilivanov.project.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgilivanov.project.model.paydoc.PayDocumentCreateRequest;
import ru.mgilivanov.project.model.paydoc.PayDocumentCreateResponse;
import ru.mgilivanov.project.model.paydoc.PayDocumentReestrRequest;
import ru.mgilivanov.project.model.paydoc.PayDocumentReestrResponse;
import ru.mgilivanov.project.service.PayDocumentService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PayDocumentController {
    private final PayDocumentService payDocumentService;

    public static final String API_PREFIX = "/api";
    public static final String PAYDOC_PREFIX = API_PREFIX + "/paydoc";
    public static final String PAYDOC_CREATE = PAYDOC_PREFIX + "/create";
    public static final String PAYDOC_REESTR = PAYDOC_PREFIX + "/reestr";

    @ApiOperation("Создание платежного документа")
    @PostMapping(PAYDOC_CREATE)
    public PayDocumentCreateResponse create(@NotNull @Validated @RequestBody PayDocumentCreateRequest request) {
        return new PayDocumentCreateResponse(payDocumentService.create(request));
    }

    @ApiOperation("Реестр платежных документов за дату")
    @PostMapping(PAYDOC_REESTR)
    public PayDocumentReestrResponse reestr(@NotNull @Validated @RequestBody PayDocumentReestrRequest request) {
        return new PayDocumentReestrResponse(payDocumentService.findAllByEodDate(request.getEodDate()));
    }

}
