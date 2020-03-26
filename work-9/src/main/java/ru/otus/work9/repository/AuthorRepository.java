package ru.otus.work9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.work9.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author genre);
}
