package ru.mgilivanov.project.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Client;
import ru.mgilivanov.project.domain.dto.ClientDto;
import ru.mgilivanov.project.model.Result;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Ответ на создание клиента")
@Accessors(chain = true)
@Data
public class ClientInfoResponse extends Result {

    @ApiModelProperty(value = "Клиенты", position = 1)
    @JsonProperty("clients")
    private List<ClientDto> clients;



    public ClientInfoResponse(List<Client> clients){
        this.clients = new ArrayList();
        clients.forEach(i -> this.clients.add(new ClientDto(i)));
    }
}
