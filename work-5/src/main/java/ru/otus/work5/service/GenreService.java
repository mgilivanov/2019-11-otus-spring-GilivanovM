package ru.otus.work5.service;

import ru.otus.work5.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findByBookId(long id);

    Genre findByName(String name);

    Genre add(String name);

    List<Genre> list();
}
