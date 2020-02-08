package ru.otus.work6.dao;

import ru.otus.work6.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findByBookId(long id);

    Optional<Genre> findByName(String name);

    List<Genre> list();
}
