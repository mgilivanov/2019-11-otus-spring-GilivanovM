package ru.mgilivanov.project.model.client;

import antlr.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Запрос на изменение клиента")
@Accessors(chain = true)
@Data
public class ClientEditRequest {
    @ApiModelProperty(value = "Идентификатор клиента", required = true, position = 1)
    @JsonProperty("id")
    @NotNull(message = "Поле 'id' должно быть задано")
    private long id;

    @ApiModelProperty(value = "Фамилия клиента", required = true, position = 2)
    @JsonProperty("last_name")
    @NotEmpty(message = "Поле 'last_name' должно быть задано")
    private String lastName;

    @ApiModelProperty(value = "Отчество клиента", position = 3)
    @JsonProperty("middle_name")
    private String middleName;

    @ApiModelProperty(value = "Имя клиента", required = true, position = 4)
    @JsonProperty("first_name")
    @NotEmpty(message = "Поле 'first_name' должно быть задано")
    private String firstName;

    @ApiModelProperty(value = "Паспорт клиента", required = true, position = 5)
    @JsonProperty("passport_id")
    @NotEmpty(message = "Поле 'passport_id' должно быть задано")
    private String passportId;

    @ApiModelProperty(value = "email", position = 6)
    @JsonProperty("email")
    private String email;


}
