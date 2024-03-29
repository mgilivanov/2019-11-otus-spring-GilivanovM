package ru.otus.work9.service;

import ru.otus.work9.domain.Book;
import ru.otus.work9.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByNameContainingIgnoreCase(String bookName);

    List<Book> findByAuthorNameContainingIgnoreCase(String name);

    List<Book> findByGenreNameContainingIgnoreCase(String name);

    List<Book> findAll();

    Book edit(long id, String name, String authorsStr, String genresStr);

    Optional<Book> findById(Long id);

    void deleteById(long id);

    List<Comment> getComments(Book book);
}
