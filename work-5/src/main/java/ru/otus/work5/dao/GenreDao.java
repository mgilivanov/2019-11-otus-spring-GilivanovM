package ru.otus.work5.dao;

import ru.otus.work5.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findByBookId(long id);

    Genre findByName(String name);

    void add(Genre genre);

    List<Genre> list();
}
