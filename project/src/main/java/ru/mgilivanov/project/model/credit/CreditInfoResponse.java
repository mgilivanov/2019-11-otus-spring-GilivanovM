package ru.mgilivanov.project.model.credit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.mgilivanov.project.domain.Account;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.model.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Информация по кредиту")
@Accessors(chain = true)
@Data
public class CreditInfoResponse extends Result {

    @ApiModelProperty(value = "Идентификатор кредита", position = 1)
    @JsonProperty("id")
    private long id;

    @ApiModelProperty(value = "Идентификатор клиента", position = 2)
    @JsonProperty("client_id")
    private long clientId;

    @ApiModelProperty(value = "Сумма кредита", position = 3)
    @JsonProperty("sum")
    private Double sum;

    @ApiModelProperty(value = "Сумма рег.платежа", position = 4)
    @JsonProperty("regular_payment")
    private Double regPayment;

    @ApiModelProperty(value = "Дата выдачи кредита", position = 5)
    @JsonProperty("issue_date")
    private LocalDate issueDate;

    @ApiModelProperty(value = "Дата следующего рег.платежа", position = 6)
    @JsonProperty("next_stmt_date")
    private LocalDate nextStmtDate;

    @ApiModelProperty(value = "Процентная ставка", position = 7)
    @JsonProperty("percent_rate")
    private Double percentRate;

    @ApiModelProperty(value = "Статус договора", position = 8)
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(value = "Счета договора", position = 9)
    @JsonProperty("accounts")
    private List<AccountDto> accounts;

    public class AccountDto{
        @ApiModelProperty(value = "Идентификатор счёта", position = 1)
        @JsonProperty("id")
        private long id;

        @ApiModelProperty(value = "Тип счёта", position = 2)
        @JsonProperty("type")
        private String type;

        @ApiModelProperty(value = "Баланс счёта", position = 3)
        @JsonProperty("balance")
        private Double balance;

        public AccountDto(Account account){
            this.id = account.getId();
            this.type = account.getAccountType().getCode();
            this.balance = account.getBalance();
        }
    }

    public CreditInfoResponse(Credit credit){
        this.accounts = new ArrayList();
        for (Account account : credit.getAccounts()){
            accounts.add(new AccountDto(account));
        }
        this.setId(credit.getId())
                .setClientId(credit.getClient().getId())
                .setSum(credit.getSum())
                .setRegPayment(credit.getRegPayment())
                .setIssueDate(credit.getIssueDate())
                .setNextStmtDate(credit.getNextStmtDate())
                .setPercentRate(credit.getPercentRate())
                .setStatus(credit.getStatus());
    }
}
