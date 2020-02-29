package ru.otus.work8.service;

import ru.otus.work8.domain.Book;
import ru.otus.work8.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByNameContainingIgnoreCase(String bookName);

    List<Book> findAll();

    Book add(String name, String authorsStr, String genresStr);

    Optional<Book> findById(String id);

    void deleteById(String id);

    List<Comment> getComments(Book book);

    List<Book> findAllByGenresId(String genreName);

    List<Book> findAllByAuthorsId(String authorId);

    Book save(Book book);

    Comment addComment(Book book, String text);
}
