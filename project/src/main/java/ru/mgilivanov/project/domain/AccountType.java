package ru.mgilivanov.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "account_types")
public class AccountType {
    public static final String GROUP_PENALTY = "PENALTY";
    public static final String GROUP_OVERDUE = "OVERDUE";
    public static final String GROUP_REGULAR = "REGULAR";

    public static final String OVERDUE_DEBT = "OVERDUE_DEBT";
    public static final String OVERDUE_PERCENTS = "OVERDUE_PERCENTS";

    public static final String PAYMENT_DEBT = "REGULAR_DEBT";
    public static final String PAYMENT_PERCENTS = "REGULAR_PERCENTS";

    public static final String USUAL_DEBT = "USUAL_DEBT";
    public static final String USUAL_PERCENTS = "USUAL_PERCENTS";

    public static final String PENALTY = "PENALTY_OVERDUE";

    public static final String UNAPPLIED_PERCENTS = "UNAPPLIED_PERCENTS";

    public static final String SERVICE = "SERVICE";

    public static final String BANK_EXTERNAL_DT = "BANK_EXTERNAL_DT";
    public static final String BANK_EXTERNAL_KT = "BANK_EXTERNAL_KT";
    public static final String BANK_PERCENTS = "BANK_PERCENTS";
    public static final String BANK_PENALTIES = "BANK_PENALTIES";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", nullable = false)
    String code;

    public String getCode() {
        return code;
    }

    @Column(name = "repayment_order")
    Integer repaymentOrder;

    @Column(name = "is_credit_account")
    boolean isCreditAccount;
}
