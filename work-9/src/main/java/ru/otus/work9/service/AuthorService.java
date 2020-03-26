package ru.otus.work9.service;

import ru.otus.work9.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findByName(String name);

    Optional<Author> findById(long id);

    Author create(String name);

    List<Author> findAll();
}
