package ru.mgilivanov.project.model.credit;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Credit;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Информация по кредитам")
@Accessors(chain = true)
@Data
public class CreditForClientResponse {
    @ApiModelProperty(value = "Кредиты", position = 1)
    @JsonProperty("credits_info")
    private List<CreditInfoResponse> creditInfoResponseList;
    public CreditForClientResponse(List<Credit> credits){
        this.creditInfoResponseList = new ArrayList();
        credits.forEach(i -> this.creditInfoResponseList.add(new CreditInfoResponse(i)));
    }
}