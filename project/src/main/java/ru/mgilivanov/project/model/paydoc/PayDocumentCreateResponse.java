package ru.mgilivanov.project.model.paydoc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import ru.mgilivanov.project.domain.PayDocument;
import ru.mgilivanov.project.model.Result;

public class PayDocumentCreateResponse extends Result {
    @ApiModelProperty(value = "Идентификатор документа", position = 1)
    @JsonProperty("id")
    private long id;

    @ApiModelProperty(value = "Статус договора", position = 1)
    @JsonProperty("status")
    private String status;

    public PayDocumentCreateResponse(PayDocument payDocument) {
        this.id = payDocument.getId();
        this.status = payDocument.getStatus();
    }
}
