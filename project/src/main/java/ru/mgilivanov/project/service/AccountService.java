package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.Account;
import ru.mgilivanov.project.domain.Credit;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(long id);

    List<Account> openCreditAccounts(Credit credit);

    void debit(Account account, Double sum);

    void credit(Account account, Double sum);

    Map<String, Account> toMap(List<Account> accounts);

    Double getFullDebt(List<Account> accounts);

    Double getGroupDebtOverdue(List<Account> accounts);

    Double getGroupDebtPenalty(List<Account> accounts);

    Double getGroupDebtRegular(List<Account> accounts);

}
