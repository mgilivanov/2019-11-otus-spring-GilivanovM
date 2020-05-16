package ru.mgilivanov.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(description = "Платежный документ")
@Accessors(chain = true)
@Data
public class PayDocumentDto {
    @ApiModelProperty(value = "Идентификатор документа", position = 1)
    @JsonProperty("id")
    private long id;

    @ApiModelProperty(value = "Тип документа", position = 2)
    @JsonProperty("type")
    private String type;

    @ApiModelProperty(value = "Дата опер.дня", position = 3)
    @JsonProperty("eod_date")
    private LocalDate eodDate;

    @ApiModelProperty(value = "Время проведения документа", position = 4)
    @JsonProperty("operation_date")
    private LocalDateTime operationDate;

    @ApiModelProperty(value = "Идентификатор счёта ДТ", position = 5)
    @JsonProperty("account_dt")
    private long accountDt;

    @ApiModelProperty(value = "Идентификатор счёта КТ", position = 6)
    @JsonProperty("account_kt")
    private long accountKt;

    @ApiModelProperty(value = "Номер внешнего счёта ДТ", position = 7)
    @JsonProperty("external_account_dt")
    private String externalAccountDt;

    @ApiModelProperty(value = "Номер внешнего счёта КТ", position = 8)
    @JsonProperty("external_account_kt")
    private String externalAccountKt;

    @ApiModelProperty(value = "Сумма документа", position = 9)
    @JsonProperty("sum")
    private Double sum;

    @ApiModelProperty(value = "Статус документа", position = 10)
    @JsonProperty("status")
    private String status;
}
