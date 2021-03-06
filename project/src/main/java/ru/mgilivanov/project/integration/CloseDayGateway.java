package ru.mgilivanov.project.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.mgilivanov.project.domain.Eod;

@MessagingGateway(errorChannel = "errorChannel")
public interface CloseDayGateway {

    @Gateway(requestChannel = "closeDayInChannel")
    void processCloseDay(@Payload Eod eod, @Header(name = "isClosed") Boolean isClosed);
}
