package ru.otus.work5.service;

import ru.otus.work5.domain.Book;

public interface PrintService {
    void print(String message);

    void print(Book book);

    void print(Object object);
}
