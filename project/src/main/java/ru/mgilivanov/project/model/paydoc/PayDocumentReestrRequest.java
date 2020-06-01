package ru.mgilivanov.project.model.paydoc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.config.FormatConfig;

import java.time.LocalDate;

@ApiModel(description = "Запрос реестра платежных документов за день")
@Accessors(chain = true)
@Data
public class PayDocumentReestrRequest {
    @ApiModelProperty(value = "Дата опер.дня", position = 1, example = FormatConfig.DATE_EXAMPLE)
    @JsonProperty("eod_date")
    @JsonFormat(pattern = FormatConfig.FORMAT_DATE)
    private LocalDate eodDate;
}
