package ru.mgilivanov.project.model.paydoc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;
import ru.mgilivanov.project.exception.BusinessLogicException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Запрос на выдачу кредита")
@Accessors(chain = true)
@Data
public class PayDocumentCreateRequest {

    @ApiModelProperty(value = "Тип платежного документа", required = true, position = 1)
    @JsonProperty("document_type")
    @NotEmpty(message = "Поле 'document_type' должно быть задано")
    private String type;

    @ApiModelProperty(value = "Дата опер.дня", position = 2, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("eod_date")
    @JsonFormat(pattern = FormatConfig.FORMAT_DATE)
    private LocalDate eodDate;

    @ApiModelProperty(value = "ID счета списания", position = 3)
    @JsonProperty("account_id_dt")
    private Long accountDt;

    @ApiModelProperty(value = "ID счета зачисления", position = 4)
    @JsonProperty("account_id_kt")
    private Long accountKt;

    @ApiModelProperty(value = "Номер внешнего счета списания", position = 5)
    @JsonProperty("external_account_dt")
    private String externalAccountDt;

    @ApiModelProperty(value = "Номер внешнего счета зачисления", position = 6)
    @JsonProperty("external_account_kt")
    private String externalAccountKt;

    @ApiModelProperty(value = "Сумма", required = true, position = 7)
    @JsonProperty("sum")
    @NotNull(message = "Поле 'sum' должно быть задано")
    @Min(value = 0, message = "Сумма документа должна быть не меньше нуля")
    private Double sum;

    public void validate(){
        if ((this.getAccountDt() == null) && (this.getExternalAccountDt() == null)) {
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_NO_ACC_DT_CODE, BusinessLogicException.DOCUMENT_NO_ACC_DT_MESSAGE);
        }
        if ((this.getAccountKt() == null) && (this.getExternalAccountKt() == null)) {
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_NO_ACC_KT_CODE, BusinessLogicException.DOCUMENT_NO_ACC_KT_MESSAGE);
        }
        if ((this.getAccountDt() != null) && (this.getExternalAccountDt() != null)) {
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_TOO_MANY_ACC_DT_CODE, BusinessLogicException.DOCUMENT_TOO_MANY_ACC_DT_MESSAGE);
        }
        if ((this.getAccountKt() != null) && (this.getExternalAccountKt() != null)) {
            throw new BusinessLogicException(BusinessLogicException.DOCUMENT_TOO_MANY_ACC_KT_CODE, BusinessLogicException.DOCUMENT_TOO_MANY_ACC_KT_MESSAGE);
        }
    }
}
