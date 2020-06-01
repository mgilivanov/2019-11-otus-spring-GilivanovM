package ru.mgilivanov.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.mgilivanov.project.domain.Credit;
import ru.mgilivanov.project.domain.Eod;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EodRepositoryTest {
    @Autowired
    private EodRepository eodRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByIdIsCorrect() {
        List<Eod> eods  = eodRepository.findByStatus("OPEN");
        assertTrue(eods.size() == 1);
        assertEquals(LocalDate.of(2020,5,6), eods.get(0).getDate());
    }
}
