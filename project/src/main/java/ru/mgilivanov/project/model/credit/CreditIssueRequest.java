package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Запрос на выдачу кредита")
@Accessors(chain = true)
@Data
public class CreditIssueRequest {
    @ApiModelProperty(value = "Идентификатор клиента", required = true, position = 1)
    @JsonProperty("client_id")
    @NotNull(message = "Поле 'client_id' должно быть задано")
    private long clientId;

    @ApiModelProperty(value = "Номер заявки", required = true, position = 2)
    @JsonProperty("applicationId")
    @NotEmpty(message = "Поле 'applicationId' должно быть задано")
    private String applicationId;

    @ApiModelProperty(value = "Процентная ставка", required = true, position = 3)
    @JsonProperty("percent_rate")
    @NotNull(message = "Поле 'percent_rate' должно быть задано")
    @Min(value = 0, message = "Значение поля 'percent_rate' должно быть не меньше 0")
    private Double percentRate;

    @ApiModelProperty(value = "Сумма кредита", required = true, position = 4)
    @JsonProperty("sum")
    @NotNull(message = "Поле 'sum' должно быть задано")
    @Min(value = 0, message = "Значение поля 'sum' должно быть не меньше 0")
    private Double sum;

    @ApiModelProperty(value = "Регулярный платеж", required = true, position = 5)
    @JsonProperty("reg_payment")
    @NotNull(message = "Поле 'reg_payment' должно быть задано")
    @Min(value = 0, message = "Значение поля 'reg_payment' должно быть не меньше 0")
    private Double regPayment;

    @ApiModelProperty(value = "Дата первого платежа", required = true, position = 6, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("first_stmt_date")
    @NotNull(message = "Поле 'first_stmt_date' должно быть задано")
    @JsonFormat(pattern = FormatConfig.FORMAT_DATE)
    private LocalDate firstStmtDate;

    @ApiModelProperty(value = "Сумма штрафа за наличие просроченной задолженности", required = true, position = 8)
    @JsonProperty("overdue_fee")
    @NotNull(message = "Поле 'overdue_fee' должно быть задано")
    @Min(value = 0, message = "Значение поля 'overdue_fee' должно быть не меньше 0")
    private Double overdueFee;
}
