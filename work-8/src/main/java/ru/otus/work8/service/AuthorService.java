package ru.otus.work8.service;

import ru.otus.work8.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findByName(String name);

    Optional<Author> findById(String id);

    Author save(Author author);

    List<Author> findAll();
}
