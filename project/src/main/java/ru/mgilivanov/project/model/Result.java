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
    public static final int FORBIDDDEN_CODE = -200;
    public static final String FORBIDDDEN_MESSAGE = "Доступ запрещен";
    public static final int VALIDATION_ERROR_CODE = -300;
    public static final int TIMEOUT_CODE = -400;
    public static final String TIMEOUT_MESSAGE = "Истекло время ожидания ответа. Попробуйте выполнить запрос позже.";

    @ApiModelProperty(value = "Код результата", position = -2)
    @JsonProperty("code")
    private int code = 0;
    @ApiModelProperty(value = "Результат", position = -1)
    @JsonProperty("message")
    private String message = "";

    public Result(Object o){
    }
}
