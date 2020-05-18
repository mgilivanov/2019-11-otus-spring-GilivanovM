package ru.otus.work11;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.work11.domain.Author;
import ru.otus.work11.domain.Book;
import ru.otus.work11.domain.Genre;
import ru.otus.work11.repository.BookRepository;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@ComponentScan
@DisplayName("Класс BookRepository")
public class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;
    private final Book EXISTS_BOOK;

    public BookServiceTest(){
        EXISTS_BOOK = new Book("1","War and Peace", new Author("Lev Tolstoy"), new Genre("Military prose"));
    }

    @Test
    void shouldCorrectFindBook() {
        Mono<Book> monoBook = bookRepository.findById(EXISTS_BOOK.getId());
        StepVerifier.create(monoBook).assertNext((book -> assertEquals(book.getName(), EXISTS_BOOK.getName()))).expectComplete().verify();

    }

    @Test
    void shouldCorrectListBooks() {
        StepVerifier.create(bookRepository.findAll()).expectNextCount(2).verifyComplete();
    }

}