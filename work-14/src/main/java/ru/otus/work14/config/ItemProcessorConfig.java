package ru.otus.work14.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.work14.domain.Book;
import ru.otus.work14.domain.Comment;
import ru.otus.work14.domain.h2.H2Author;
import ru.otus.work14.domain.h2.H2Book;
import ru.otus.work14.domain.h2.H2Comment;
import ru.otus.work14.domain.h2.H2Genre;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ItemProcessorConfig {
    @StepScope
    @Bean
    public ItemProcessor<Book, H2Author> authorImportProcessor() {
        Set<String> authorNames = new HashSet<>();
        return book -> {
            H2Author author = new H2Author(book.getAuthor().getName());
            if (authorNames.add(author.getName())) {
                return author;
            } else {
                return null;
            }
        };
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, H2Genre> genreImportProcessor() {
        Set<String> genreNames = new HashSet<>();
        return book -> {
            H2Genre genre = new H2Genre(book.getGenre().getName());
            if (genreNames.add(genre.getName())) {
                return genre;
            } else {
                return null;
            }
        };
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, H2Book> bookImportProcessor() {
        return book -> {
            H2Author h2Author = new H2Author(book.getAuthor().getName());
            H2Genre h2Genre = new H2Genre(book.getGenre().getName());
            H2Book h2Book = new H2Book(book.getName(), h2Author, h2Genre);
            return h2Book;
        };
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, H2Comment> commentImportProcessor() {
        Set<H2Book> comments = new HashSet<>();
        return comment -> {
            H2Comment h2Comment = new H2Comment(new H2Book(comment.getBook().getName()), comment.getText(), comment.getCreateDate());
            return h2Comment;
        };
    }
}
