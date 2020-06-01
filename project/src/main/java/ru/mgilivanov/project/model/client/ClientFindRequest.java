package ru.mgilivanov.project.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "Запрос на поиск клиентов")
@Accessors(chain = true)
@Data
public class ClientFindRequest {
    @ApiModelProperty(value = "Паспорт клиента", position = 1)
    @JsonProperty("passport_id")
    private String passportId;

    @ApiModelProperty(value = "Фамилия клиента", position = 2)
    @JsonProperty("last_name")
    private String lastName;

    @ApiModelProperty(value = "Отчество клиента", position = 3)
    @JsonProperty("middle_name")
    private String middleName;

    @ApiModelProperty(value = "Имя клиента", position = 4)
    @JsonProperty("first_name")
    private String firstName;

    public ClientFindRequest init(){
        if (this.lastName == null){this.lastName = "";}
        if (this.middleName == null){this.middleName = "";}
        if (this.firstName == null){this.firstName = "";}
        if (this.passportId == null){this.passportId = "";}
        return this;
    }
}
