package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    Optional<AccountType> findById(long id);

    Optional<AccountType> findByCode(String code);

    List<AccountType> findAllByIsCreditAccount(boolean isCreditAccount);
}
