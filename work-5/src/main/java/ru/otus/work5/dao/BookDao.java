package ru.otus.work5.dao;

import ru.otus.work5.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> find(String bookName, String authorName, String genreName);

    void add(Book book);

    boolean deleteById(long id);
}
