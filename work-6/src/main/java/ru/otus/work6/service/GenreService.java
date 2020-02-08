package ru.otus.work6.service;

import ru.otus.work6.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findByBookId(long id);

    Optional<Genre> findByName(String name);

    public Genre create(String name);

    List<Genre> list();
}
