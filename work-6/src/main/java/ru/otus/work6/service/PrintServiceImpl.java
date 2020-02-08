package ru.otus.work6.service;

import org.springframework.stereotype.Service;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Genre;

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
    public void print(Book book) {
            String bookStr = messageService.getMessage("book.id") + book.getId() + "; " + messageService.getMessage("book.name") + book.getName() + "; ";
            String authorsStr = new String(messageService.getMessage("author.name"));
            String genresStr = new String(messageService.getMessage("genre.name"));
            for (Author author : book.getAuthors()) {
                authorsStr = authorsStr + author.toString() + ", ";
            }
            for (Genre genre : book.getGenres()) {
                genresStr = genresStr + genre.toString() + ", ";
            }
            print(bookStr + authorsStr.replaceFirst(",\\s$","") + "; " + genresStr.replaceFirst(",\\s$","") + ".");
    }

    @Override
    public void print(Object object) {
        this.out.println(object.toString());
    }
}
