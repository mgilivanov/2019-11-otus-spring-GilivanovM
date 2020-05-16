package ru.mgilivanov.project.model.credit;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "Запрос списка договоров, по которым не закрыт предыдущий день")
@Accessors(chain = true)
@Data
public class CreditEodFailedRequest {

}
