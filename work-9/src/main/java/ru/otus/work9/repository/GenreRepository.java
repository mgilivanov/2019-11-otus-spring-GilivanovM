package ru.otus.work9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.work9.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Genre save(Genre genre);
}
