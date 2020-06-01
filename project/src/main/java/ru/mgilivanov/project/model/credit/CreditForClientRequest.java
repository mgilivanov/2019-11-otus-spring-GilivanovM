package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Запрос информации о договорах клиента")
@Accessors(chain = true)
@Data
public class CreditForClientRequest {
    @ApiModelProperty(value = "Идентификатор клиента", required = true, position = 1)
    @JsonProperty("client_id")
    @NotNull(message = "Поле 'client_id' должно быть задано")
    private long clientId;
}
