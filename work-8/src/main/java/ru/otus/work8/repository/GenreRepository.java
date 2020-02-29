package ru.otus.work8.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

    Optional<Genre> findById(String id);

    List<Genre> findAll();

    Genre save(Genre genre);
}
