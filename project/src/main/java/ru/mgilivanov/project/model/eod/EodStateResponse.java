package ru.mgilivanov.project.model.eod;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.model.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(description = "Состояние операционного дня")
@Accessors(chain = true)
@Data
public class EodStateResponse extends Result {
    @ApiModelProperty(value = "Состояние", position = 1)
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(value = "Дата открытого дня", position = 2)
    @JsonProperty("open_date")
    private LocalDate openDate;

    @ApiModelProperty(value = "Дата закрываемого дня", position = 3)
    @JsonProperty("closing_date")
    private LocalDate closingDate;

    @ApiModelProperty(value = "Время начала закрытия дня", position = 4)
    @JsonProperty("start_closing_time")
    private LocalDateTime startClosingTime;
}
