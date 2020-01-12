package ru.otus.work5.dao;

import ru.otus.work5.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findByBookId(long id);

    Author findByName(String name);

    void add(Author author);

    List<Author> list();
}
