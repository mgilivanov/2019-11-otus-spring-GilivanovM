package ru.otus.work6.service;

import ru.otus.work6.domain.Book;

public interface PrintService {
    void print(String message);

    void print(Book book);

    void print(Object object);
}
