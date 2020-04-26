package ru.otus.work17;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.work17.domain.Author;
import ru.otus.work17.domain.Book;
import ru.otus.work17.domain.Genre;
import ru.otus.work17.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Класс BookService")
public class BookRepositoryTest extends AbstractMongoIntegrationTest {
    @Autowired
    BookRepository repository;

    private final Book NEW_BOOK = new Book("4","New Book", new Author("New Book Author"), new Genre("New Book Genre"));

    @Test
    @DisplayName("корректно добавляет новую книгу")
    void shouldAddCorrect(){
        assertEquals(0, repository.findAll().size());
        assertEquals(NEW_BOOK, repository.save(new Book("4","New Book",new Author("New Book Author"),new Genre("New Book Genre"))));
        assertEquals(1, repository.findAll().size());
    }

}