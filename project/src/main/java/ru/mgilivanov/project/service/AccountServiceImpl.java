package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.Account;
import ru.mgilivanov.project.domain.AccountType;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.exception.ApplicationException;
import ru.mgilivanov.project.repository.AccountRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeService accountTypeService;

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account getBankAccountDt() {
        return (accountRepository.findByAccountTypeCode(AccountType.BANK_EXTERNAL_DT))
                .orElseThrow(() -> new ApplicationException(ApplicationException.NO_BANK_ACC_DT_CODE, ApplicationException.NO_BANK_ACC_DT_MESSAGE));
    }

    @Override
    public Account getBankAccountKt() {
        return (accountRepository.findByAccountTypeCode(AccountType.BANK_EXTERNAL_KT))
                .orElseThrow(() -> new ApplicationException(ApplicationException.NO_BANK_ACC_KT_CODE, ApplicationException.NO_BANK_ACC_KT_MESSAGE));
    }

    @Override
    public Account getBankAccountPercents(){
        return (accountRepository.findByAccountTypeCode(AccountType.BANK_PERCENTS))
                .orElseThrow(() -> new ApplicationException(ApplicationException.NO_BANK_ACC_PERCENTS_CODE, ApplicationException.NO_BANK_ACC_PERCENTS_MESSAGE));
    }

    @Override
    public Account getBankAccountPenalty(){
        return (accountRepository.findByAccountTypeCode(AccountType.BANK_PENALTIES))
                .orElseThrow(() -> new ApplicationException(ApplicationException.NO_BANK_ACC_PENALTY_CODE, ApplicationException.NO_BANK_ACC_PENALTY_MESSAGE));
    }


    @Override
    public List<Account> openCreditAccounts(Credit credit) {
        List<Account> accounts = new ArrayList<>();
        for (AccountType accountType : accountTypeService.getAllCreditAccountTypes()){
            Account account = new Account().setCredit(credit).setAccountType(accountType).setBalance(0d);
            accounts.add(account);
            accountRepository.save(account);
         }
        return accounts;
    }

    @Override
    public void debit(Account account, Double sum) {
        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);
    }

    @Override
    public void credit(Account account, Double sum) {
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);
    }

    public Map<String, Account> toMap(List<Account> accounts){
        return accounts.stream().collect(Collectors.toMap(p -> p.getAccountType().getCode(), Function.identity()));
    }

    @Override
    public Double getFullDebt(List<Account> accounts){
        return (-1) * accounts.stream()
                .filter(i -> i.getAccountType().getRepaymentOrder() != null)
                .mapToDouble(i -> i.getBalance()).sum();
    }

    private Double getGroupDebt(List<Account> accounts, String debtGroup){
        return (-1) * accounts.stream()
                .filter(i -> i.getAccountType().getCode().startsWith(debtGroup))
                .mapToDouble(i -> i.getBalance()).sum();
    }

    @Override
    public Double getGroupDebtOverdue(List<Account> accounts){
        return getGroupDebt(accounts, AccountType.GROUP_OVERDUE);
    }

    @Override
    public Double getGroupDebtPenalty(List<Account> accounts){
        return getGroupDebt(accounts, AccountType.GROUP_PENALTY);
    }

    @Override
    public Double getGroupDebtRegular(List<Account> accounts){
        return getGroupDebt(accounts, AccountType.GROUP_REGULAR);
    }

}
