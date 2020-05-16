package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account save(Account account);

    Optional<Account> findById(long id);

    Optional<Account> findByAccountTypeCode(String code);

}