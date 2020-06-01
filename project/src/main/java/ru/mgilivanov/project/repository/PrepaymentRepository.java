package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Prepayment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrepaymentRepository extends JpaRepository<Prepayment, Long> {

    Prepayment save(Prepayment prepayment);

    Optional<Prepayment> findById(long id);

    List<Prepayment> findAllByDateAndStatusOrderById(LocalDate date, String status);

    List<Prepayment> findAllByCreditAndDateAndStatus(Credit credit, LocalDate date, String status);
}
