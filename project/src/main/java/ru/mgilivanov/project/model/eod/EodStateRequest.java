package ru.mgilivanov.project.model.eod;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "Запрос состояния опер.дня")
@Accessors(chain = true)
@Data
public class EodStateRequest {
}
