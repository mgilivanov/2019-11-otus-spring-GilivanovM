package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Запрос информации по договору")
@Accessors(chain = true)
@Data
public class CreditInfoRequest {
    @ApiModelProperty(value = "Идентификатор кредита", required = true, position = 1)
    @JsonProperty("credit_id")
    @NotNull(message = "Поле 'credit_id' должно быть задано")
    private long creditId;
}
