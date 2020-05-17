package ru.otus.work18.service;

import ru.otus.work18.domain.Book;
import ru.otus.work18.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Book edit(String id, String name, String authorsStr, String genresStr);

    Optional<Book> findById(String id);

    void deleteById(String id);

    List<Comment> getComments(Book book);

    void addComment(Book book, Comment comment);
}
