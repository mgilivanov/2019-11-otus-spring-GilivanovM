package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;

import java.time.LocalDate;

@ApiModel(description = "Запрос на выдачу кредита")
@Accessors(chain = true)
@Data
public class CreditReestrRequest {
    @ApiModelProperty(value = "Дата опер.дня", position = 1, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("issue_date")
    @JsonFormat(pattern = FormatConfig.FORMAT_DATE)
    private LocalDate issueDate;
}
