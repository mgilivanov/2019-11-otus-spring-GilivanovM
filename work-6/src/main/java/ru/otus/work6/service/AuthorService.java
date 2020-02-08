package ru.otus.work6.service;

import ru.otus.work6.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findByBookId(long id);

    Optional<Author> findByName(String name);

    public Author create(String name);

    List<Author> list();
}
