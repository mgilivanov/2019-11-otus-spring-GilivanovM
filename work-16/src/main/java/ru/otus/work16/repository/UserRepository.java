package ru.otus.work16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.work16.domain.User;

import java.util.Optional;
@RepositoryRestResource(exported = false)
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByName(String name);
}
