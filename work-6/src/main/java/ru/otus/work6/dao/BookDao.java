package ru.otus.work6.dao;

import ru.otus.work6.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> find(String bookName, String authorName, String genreName);

    void add(Book book);

    Optional<Book> findById(Long id);

    boolean deleteById(long id);
}
