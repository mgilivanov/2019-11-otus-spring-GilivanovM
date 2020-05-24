package ru.mgilivanov.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.mgilivanov.project.domain.Credit;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CreditRepositoryTest {
    @Autowired
    private CreditRepository creditRepository;

    @Test
    void findByIdIsCorrect() {
        Optional<Credit> optCredit  = creditRepository.findById(1);
        assertTrue(optCredit.isPresent());
        assertEquals(100000, optCredit.get().getSum());
    }
}
