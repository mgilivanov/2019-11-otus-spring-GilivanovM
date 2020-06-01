package ru.mgilivanov.project.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "pay_documents")
public class PayDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "eod_date", nullable = false)
    private LocalDate eodDate;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "account_id_dt", nullable = false)
    private Account accountDt;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "account_id_kt", nullable = false)
    private Account accountKt;

    @Column(name = "external_account_dt")
    private String externalAccountDt;

    @Column(name = "external_account_kt")
    private String externalAccountKt;

    @Column(name = "sum", nullable = false)
    private Double sum;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_external", nullable = false)
    private boolean isExternal;

    @AllArgsConstructor
    @Getter
    public class Type{
        public static final String ISSUE = "ISSUE";
        public static final String REPAYMENT_OVERDUE = "REPAYMENT_OVERDUE";
        public static final String REPAYMENT_REGULAR = "REPAYMENT_REGULAR";
        public static final String REPAYMENT_PENALTY = "REPAYMENT_PENALTY";
        public static final String REPAYMENT_PREPAYMENT = "REPAYMENT_PREPAYMENT";
        public static final String ALLOTMENT_PAYMENT = "ALLOTMENT_PAYMENT";
        public static final String TRANSFER_TO_OVERDUE = "TRANSFER_TO_OVERDUE";
        public static final String ACCRUAL_INTEREST = "ACCRUAL_INTEREST";
        public static final String CAPITALIZATION_PERCENTS = "CAPITALIZATION_PERCENTS";
        public static final String EARLY_REPAYMENT = "EARLY_REPAYMENT";
        public static final String CHARGE_FINE = "CHARGE_FINE";
        public static final String EXTERNAL_DT = "EXTERNAL_DT";
        public static final String EXTERNAL_KT = "EXTERNAL_KT";
    }

    @AllArgsConstructor
    @Getter
    public class Status{
        public final static String NEW = "NEW";
        public final static String COMPLETED = "COMPLETED";
        public final static String WAIT_VALUE_DATE = "WAIT_VALUE_DATE";
    }
}
