package ru.otus.work8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work8.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Optional<Author> findById(String id);

    List<Author> findAll();

    Author save(Author genre);
}
