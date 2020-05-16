package ru.mgilivanov.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "prepayment_applications")
public class Prepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(targetEntity = Credit.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(name = "is_full", nullable = false)
    private boolean isFull;

    @Column(name = "sum", nullable = false)
    private Double sum;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    private String status;

    @AllArgsConstructor
    @Getter
    public class Status{
        public static final String NEW = "NEW";
        public static final String COMPLETED = "COMPLETED";
        public static final String REJECTED = "REJECTED";
    }
}
