package ru.mgilivanov.project.service;

import ru.mgilivanov.project.domain.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountTypeService {
    Optional<AccountType> findByCode(String code);

    List<AccountType> getAllCreditAccountTypes();
}
