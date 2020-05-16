package ru.mgilivanov.project.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "credits")
public class Credit {
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_CLOSED = "CLOSED";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "application_id", nullable = false)
    private String applicationId;

    @Column(name = "percent_rate", nullable = false)
    private Double percentRate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "sum", nullable = false)
    private Double sum;

    @Column(name = "reg_payment", nullable = false)
    private Double regPayment;

    @Column(name = "next_stmt_date", nullable = false)
    private LocalDate nextStmtDate;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "stmt_day", nullable = false)
    private int stmtDay;

    @Column(name = "overdue_fee", nullable = false)
    private Double overdueFee;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "credit")
    private List<Account> accounts;

    @Column(name = "last_processed_date")
    private LocalDate lastProcessedDate;

    @Column(name = "last_processed_message")
    private String lastProcessedMessage;

    @AllArgsConstructor
    @Getter
    public class Status{
        public final static String ACTIVE = "ACTIVE";
        public final static String CLOSED = "CLOSED";
    }

}
