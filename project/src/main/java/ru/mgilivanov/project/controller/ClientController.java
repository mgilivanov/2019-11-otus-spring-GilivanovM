package ru.mgilivanov.project.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.mgilivanov.project.model.client.*;
import ru.mgilivanov.project.domain.dto.ClientDto;
import ru.mgilivanov.project.service.ClientService;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientController {
    private final ClientService clientService;
    public static final String API_PREFIX = "/api";
    public static final String CLIENT_PREFIX = API_PREFIX + "/client";
    public static final String CLIENT_ADD = CLIENT_PREFIX + "/add";
    public static final String CLIENT_EDIT = CLIENT_PREFIX + "/edit";
    public static final String CLIENT_INFO = CLIENT_PREFIX + "/find";

    @ApiOperation("Создание клиента")
    @PostMapping(value = CLIENT_ADD,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            , @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    public ClientDto add(@NotNull @Validated @RequestBody ClientAddRequest request) {
        return new ClientDto(clientService.add(request));
    }

    @ApiOperation("Изменение клиента")
    @PostMapping(CLIENT_EDIT)
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            , @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    public ClientDto edit(@NotNull @Validated @RequestBody ClientEditRequest request) {
        return new ClientDto(clientService.edit(request));
    }

    @ApiOperation("Поиск клиента по паспорту")
    @PostMapping(CLIENT_INFO)
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            , @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    public ClientInfoResponse find(@NotNull @Validated @RequestBody ClientFindRequest request) {
        return new ClientInfoResponse(clientService.find(request.init()));
    }
}
