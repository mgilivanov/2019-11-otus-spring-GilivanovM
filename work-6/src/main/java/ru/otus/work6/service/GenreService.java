package ru.otus.work6.service;

import ru.otus.work6.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> findByName(String name);

    Genre create(String name);

    Optional<Genre> findById(long id);

    List<Genre> list();
}
