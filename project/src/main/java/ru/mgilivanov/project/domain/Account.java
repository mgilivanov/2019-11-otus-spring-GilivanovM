package ru.mgilivanov.project.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Credit.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToOne(targetEntity = AccountType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_type")
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

}
