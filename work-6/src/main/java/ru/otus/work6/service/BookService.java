package ru.otus.work6.service;

import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findByName(String bookName);

    List<Book> findAll();

    Book add(String name, String authorsStr, String genresStr);

    Optional<Book> findById(Long id);

    boolean deleteById(long id);

    List<Comment> getComments(Book book);
}
