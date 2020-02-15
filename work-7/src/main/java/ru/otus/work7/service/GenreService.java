package ru.otus.work7.service;

import ru.otus.work7.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<Genre> findByName(String name);

    Genre create(String name);

    Optional<Genre> findById(long id);

    List<Genre> findAll();
}
