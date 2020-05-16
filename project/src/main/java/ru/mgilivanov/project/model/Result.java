package ru.mgilivanov.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(description = "Ответ на запрос")
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Result {
    public static final int CODE_OK = 0;
    public static final int BAD_JSON_CODE = -100;
    public static final String BAD_JSON_MESSAGE = "Запрос не соответствует JSON";
    public static final int VALIDATION_ERROR_CODE = -200;

    @ApiModelProperty(value = "Код результата", required = false, position = -2)
    @JsonProperty("code")
    private int code = 0;
    @ApiModelProperty(value = "Результат", required = false, position = -1)
    @JsonProperty("message")
    private String message = "";

    public Result(Object o){
    }
}
