package ru.otus.work13.service;

import ru.otus.work13.domain.Book;
import ru.otus.work13.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByNameContainingIgnoreCase(String bookName);

    List<Book> findByAuthorNameContainingIgnoreCase(String name);

    List<Book> findByGenreNameContainingIgnoreCase(String name);

    List<Book> findAll();

    Book edit(String id, String name, String authorsStr, String genresStr);

    Optional<Book> findById(String id);

    void deleteById(String id);

    List<Comment> getComments(Book book);

    void addComment(Book book, Comment comment);
}
