package ru.mgilivanov.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.mgilivanov.project.domain.Client;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByIdIsCorrect() {
        Optional<Client> optClient  = clientRepository.findById(1);
        assertTrue(optClient.isPresent());
        assertEquals("Ivanov", optClient.get().getLastName());
    }

    @Test
    void saveClientTest() {
        Client newclient = new Client()
                .setFirstName("Dmitry")
                .setLastName("Andreev")
                .setMiddleName("Igorevich")
                .setPassportId("0101 444444")
                .setEmail("dfd@nomail.ru");

        clientRepository.save(newclient);
        entityManager.detach(newclient);
        Client client = entityManager.find(Client.class, newclient.getId());
        assertEquals(newclient.getId(), client.getId());
    }
}
