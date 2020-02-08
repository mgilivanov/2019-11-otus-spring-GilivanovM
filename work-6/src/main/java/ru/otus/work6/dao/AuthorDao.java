package ru.otus.work6.dao;

import ru.otus.work6.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findByBookId(long id);

    Optional<Author> findByName(String name);

    List<Author> list();
}
