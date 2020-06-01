package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.model.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Информация по договорам с ошибками в закрытии дня")
@Accessors(chain = true)
@Data
public class CreditEodFailedResponse extends Result {
    @ApiModelProperty(value = "Кредиты", position = 1)
    @JsonProperty("credits_info")
    private List<FailedCredits> failedCreditsList;

    @Accessors(chain = true)
    @Data
    public class FailedCredits{
        @ApiModelProperty(value = "Идентификатор кредита", position = 1)
        @JsonProperty("id")
        private long id;

        @ApiModelProperty(value = "Дата последнего закрытого дня", position = 2)
        @JsonProperty("last_processed_date")
        private LocalDate lastProcessedDate;

        @ApiModelProperty(value = "Сообщение об ошибке", position = 3)
        @JsonProperty("last_processed_message")
        private String lastProcessedMessage;
    }

    public CreditEodFailedResponse(List<Credit> credits){
        this.failedCreditsList = new ArrayList();
        credits.forEach(i -> this.failedCreditsList.add(new FailedCredits().setLastProcessedDate(i.getLastProcessedDate()).setLastProcessedMessage(i.getLastProcessedMessage())));
    }
}
