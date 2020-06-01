package ru.mgilivanov.project.model.eod;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(description = "Запрос на закрытие опер.дня")
@Accessors(chain = true)
@Data
public class CloseDayRequest {
    @ApiModelProperty(value = "Дата закрываемого дня", required = true, position = 1, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("date")
    @NotNull(message = "Поле 'date' должно быть задано")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConfig.FORMAT_DATE)
    private LocalDate date;
}
