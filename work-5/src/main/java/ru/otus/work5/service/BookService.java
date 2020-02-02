package ru.otus.work5.service;

import ru.otus.work5.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> find(String bookName, String authorName, String genreName);

    Book add(String name, String authorsStr, String genresStr);

    boolean deleteById(long id);
}
