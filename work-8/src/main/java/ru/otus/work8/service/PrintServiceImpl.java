package ru.otus.work8.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PrintServiceImpl implements PrintService {
    private final PrintStream out;
    private final MessageService messageService;

    public PrintServiceImpl(MessageService messageService) {
        this.messageService = messageService;
        this.out = System.out;
    }

    @Override
    public void print(String message) {
        this.out.println(message);
    }

    @Override
    public void print(Object object) {
        this.out.println(object.toString());
    }
}
