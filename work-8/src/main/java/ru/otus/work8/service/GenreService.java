package ru.otus.work8.service;

import ru.otus.work8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Genre save(Genre genre);
    List<Genre> findAll();
    Optional<Genre> findByName(String name);
}
