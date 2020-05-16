package ru.mgilivanov.project.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.model.Result;

@ApiModel(description = "Ответ на создание клиента")
@Accessors(chain = true)
@Data
public class ClientSaveResponse extends Result {

    @ApiModelProperty(value = "Идентификатор клиента", position = 1)
    @JsonProperty("id")
    private long id;
}
