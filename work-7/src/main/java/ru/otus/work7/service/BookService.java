package ru.otus.work7.service;

import ru.otus.work7.domain.Book;
import ru.otus.work7.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByNameContainingIgnoreCase(String bookName);

    List<Book> findAll();

    Book add(String name, String authorsStr, String genresStr);

    Optional<Book> findById(Long id);

    void deleteById(long id);

    List<Comment> getComments(Book book);
}
