package ru.otus.work17.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work17.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByName(String name);
}
