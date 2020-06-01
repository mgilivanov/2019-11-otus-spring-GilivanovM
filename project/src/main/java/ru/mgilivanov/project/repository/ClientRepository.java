package ru.mgilivanov.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mgilivanov.project.domain.Client;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Client save(Client client);

    Optional<Client> findById(long id);

    List<Client> findByPassportIdLikeAndLastNameLikeAndFirstNameLikeAndMiddleNameLike(String passportId, String lastName, String firstName, String middleName);
}
