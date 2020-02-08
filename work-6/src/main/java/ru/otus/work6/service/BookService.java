package ru.otus.work6.service;

import ru.otus.work6.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> find(String bookName, String authorName, String genreName);

    Book add(String name, String authorsStr, String genresStr);

    Optional<Book> findById(Long id);

    boolean deleteById(long id);
}
