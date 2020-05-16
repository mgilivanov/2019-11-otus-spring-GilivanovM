package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;
import ru.mgilivanov.project.exception.BusinessLogicException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Создание заявки на частично-досрочное погашение")
@Accessors(chain = true)
@Data
public class CreditPrepaymentRequest {
    @ApiModelProperty(value = "Идентификатор кредитного договора", position = 1)
    @JsonProperty(value = "credit_id", required = true)
    @NotNull(message = "Поле 'credit_id' должно быть задано")
    private Long creditId;

    @ApiModelProperty(value = "Признак полного погашения", position = 2)
    @JsonProperty(value = "is_full", required = true)
    private Boolean isFull;

    @ApiModelProperty(value = "Сумма частичного погашения", allowEmptyValue = true, position = 3)
    @JsonProperty("sum")
    @Min(value = 0, message = "Сумма погашения не должна быть меньше 0")
    private Double sum;

    @ApiModelProperty(value = "Дата погашения", allowEmptyValue = true, position = 4, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("date")
    @JsonFormat(pattern = FormatConfig.FORMAT_DATE)
    private LocalDate date;


    public void validate(){
        if ((!this.getIsFull()) && this.getSum() == null){
            throw new BusinessLogicException(BusinessLogicException.PREPAYMENT_NO_SUM_CODE, BusinessLogicException.PREPAYMENT_NO_SUM_MESSAGE);
        }
    }
}
