package ru.mgilivanov.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Client;

@ApiModel(description = "Информация о клиенте")
@Accessors(chain = true)
@Data
public class ClientDto {
    @ApiModelProperty(value = "Идентификатор клиента", position = 1)
    @JsonProperty("id")
    private long id;

    @ApiModelProperty(value = "Фамилия клиента", position = 2)
    @JsonProperty("last_name")
    private String lastName;

    @ApiModelProperty(value = "Имя клиента", position = 3)
    @JsonProperty("first_name")
    private String firstName;

    @ApiModelProperty(value = "Отчество клиента", position = 4)
    @JsonProperty("middle_name")
    private String middleName;

    @ApiModelProperty(value = "Паспорт клиента", position = 5)
    @JsonProperty("passport_id")
    private String passportId;

    @ApiModelProperty(value = "email клиента", position = 6)
    @JsonProperty("email")
    private String email;

    public ClientDto(Client client) {
        if (client != null) {
            this.id = client.getId();
            this.lastName = client.getLastName();
            this.firstName = client.getFirstName();
            this.middleName = client.getMiddleName();
            this.passportId = client.getPassportId();
            this.email = client.getEmail();
        }
    }
}
