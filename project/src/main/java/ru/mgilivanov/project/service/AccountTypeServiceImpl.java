package ru.mgilivanov.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mgilivanov.project.domain.AccountType;
import ru.mgilivanov.project.repository.AccountTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    @Override
    public Optional<AccountType> findByCode(String code) {
        return accountTypeRepository.findByCode(code);
    }

    @Override
    public List<AccountType> getAllCreditAccountTypes() {
        return accountTypeRepository.findAllByIsCreditAccount(true);
    }
}
