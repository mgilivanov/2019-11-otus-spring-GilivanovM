package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.Eod;

import java.util.List;
import java.util.Optional;

public interface EodRepository extends JpaRepository<Eod, Long> {
    List<Eod> findByStatus(String status);
    Optional<Eod> findFirstByStatus(String status);
    Eod save(Eod eod);
}
