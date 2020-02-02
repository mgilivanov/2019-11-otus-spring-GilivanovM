package ru.otus.work5.service;

import ru.otus.work5.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findByBookId(long id);

    Author findByName(String name);

    Author add(String name);

    List<Author> list();
}
