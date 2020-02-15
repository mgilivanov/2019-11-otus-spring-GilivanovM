package ru.otus.work7;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work7.domain.Author;
import ru.otus.work7.domain.Book;
import ru.otus.work7.domain.Genre;
import ru.otus.work7.service.BookService;
import ru.otus.work7.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс BookService")
@Transactional
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;

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
        assertEquals(EXISTS_BOOKS, bookService.findByNameContainingIgnoreCase("war"));
    }

    @Test
    @DisplayName("корректно добавляет новую книгу")
    void shouldAddCorrect(){
        assertEquals(3, bookService.findAll().size());
        assertEquals(NEW_BOOK, bookService.add("New Book","Lev Tolstoy,New Book Author","Novel,New Book Genre"));
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    @DisplayName("корректно читает комментарии к книге")
    void shouldFindCommentsCorrect(){
        Optional<Book> book = bookService.findById(EXISTS_BOOKS.get(0).getId());
        assertEquals(2, bookService.getComments(book.get()).size());
    }
}