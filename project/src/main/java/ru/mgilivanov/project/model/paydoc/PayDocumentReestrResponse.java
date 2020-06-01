package ru.mgilivanov.project.model.paydoc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import ru.mgilivanov.project.domain.PayDocument;
import ru.mgilivanov.project.domain.dto.PayDocumentDto;
import ru.mgilivanov.project.model.Result;

import java.util.ArrayList;
import java.util.List;

public class PayDocumentReestrResponse extends Result {

    @ApiModelProperty(value = "Документы", position = 1)
    @JsonProperty("documents")
    private List<PayDocumentDto> payDocumentDtoList;

    public PayDocumentReestrResponse(List<PayDocument> payDocuments) {
        this.payDocumentDtoList = new ArrayList();
        payDocuments.forEach(i -> this.payDocumentDtoList.add(
                new PayDocumentDto()
                        .setType(i.getType())
                        .setEodDate(i.getEodDate())
                        .setOperationDate(i.getOperationDate())
                        .setAccountDt((i.getAccountDt() == null) ? null : i.getAccountDt().getId())
                        .setAccountKt((i.getAccountKt() == null) ? null : i.getAccountKt().getId())
                        .setExternalAccountDt(i.getExternalAccountDt())
                        .setExternalAccountKt(i.getExternalAccountKt())
                        .setId(i.getId())
                        .setStatus(i.getStatus())
                        .setSum(i.getSum())
        ));
    }
}
