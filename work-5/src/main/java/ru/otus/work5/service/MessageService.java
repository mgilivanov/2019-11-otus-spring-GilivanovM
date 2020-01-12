package ru.otus.work5.service;

public interface MessageService {
    String getMessage(String messageCode, String[] args);

    String getMessage(String messageCode);

}
