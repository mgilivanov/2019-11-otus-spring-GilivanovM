package ru.otus.work6.dao;

import ru.otus.work6.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> findByName(String name);

    Optional<Author> findById(long id);

    List<Author> list();

    void update(Author author);
}
