package ru.otus.work6;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Genre;
import ru.otus.work6.service.BookService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс BookService")
@Transactional
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    private final List<Book> EXISTS_BOOKS;
    private final Book NEW_BOOK;

    public BookServiceTest(){
        Book existsBook = new Book(1,"War and Peace");
        existsBook.addAuthor(new Author(1,"Lev Tolstoy"));
        existsBook.addGenre(new Genre(1,"Novel"));
        existsBook.addGenre(new Genre(2,"Military prose"));
        EXISTS_BOOKS = new ArrayList<>();
        EXISTS_BOOKS.add(existsBook);

        NEW_BOOK = new Book(4,"New Book");
        NEW_BOOK.addAuthor(new Author(1,"Lev Tolstoy"));
        NEW_BOOK.addAuthor(new Author(5,"New Book Author"));
        NEW_BOOK.addGenre(new Genre(1,"Novel"));
        NEW_BOOK.addGenre(new Genre(5,"New Book Genre"));
    }

    @Test
    @DisplayName("корректно ищет книги по названию")
    void shouldFindCorrect() {
        assertEquals(EXISTS_BOOKS, bookService.find("War and Peace","",""));
    }

    @Test
    @DisplayName("корректно ищет книги по автору")
    void shouldFindByAuthorCorrect() {
        assertEquals(EXISTS_BOOKS, bookService.find("","Tolstoy",""));
    }

    @Test
    @DisplayName("корректно ищет книги по названию и жанру")
    void shouldFindByGenreCorrect() {
        assertEquals(EXISTS_BOOKS, bookService.find("War","","Military prose"));
    }

    @Test
    @DisplayName("корректно добавляет новую книгу")
    void shouldAddCorrect(){
        assertEquals(3, bookService.find("","","").size());
        assertEquals(NEW_BOOK, bookService.add("New Book","Lev Tolstoy,New Book Author","Novel,New Book Genre"));
        assertEquals(4, bookService.find("","","").size());
    }

}