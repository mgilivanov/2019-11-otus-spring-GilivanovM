package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.PayDocument;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PayDocumentRepository extends JpaRepository<PayDocument, Long> {

    PayDocument save(PayDocument payDocument);

    Optional<PayDocument> findById(long id);

    List<PayDocument> findAllByEodDateAndStatus(LocalDate date, String status);

    List<PayDocument> findAllByEodDate(LocalDate date);
}
