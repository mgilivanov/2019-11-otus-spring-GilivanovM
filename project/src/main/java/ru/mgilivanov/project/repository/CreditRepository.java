package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.Credit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long>  {

    Credit save(Credit credit);

    Optional<Credit> findById(long id);

    List<Credit> findAll();

    List<Credit> findAllByStatus(String status);

    List<Credit> findAllByIssueDate(LocalDate issueDate);

    List<Credit> findAllByClientId(long clientId);

    List<Credit> findAllByLastProcessedDateBeforeAndIssueDateBefore(LocalDate lastProcessedDate, LocalDate issueDate);

}
