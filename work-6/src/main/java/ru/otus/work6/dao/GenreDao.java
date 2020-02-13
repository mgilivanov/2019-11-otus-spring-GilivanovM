package ru.otus.work6.dao;

import ru.otus.work6.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> findByName(String name);

    Optional<Genre> findById(long id);

    List<Genre> list();

    void update(Genre genre);
}
