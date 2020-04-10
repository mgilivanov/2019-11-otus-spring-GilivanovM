package ru.otus.work9;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work9.domain.Author;
import ru.otus.work9.domain.Book;
import ru.otus.work9.domain.Genre;
import ru.otus.work9.service.BookService;
import ru.otus.work9.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("Класс BookService")
@Transactional
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;

    private final Book EXISTS_BOOK;
    private final Book NEW_BOOK;

    public BookServiceTest(){
        EXISTS_BOOK = new Book(1,"War and Peace", new Author(1,"Lev Tolstoy"), new Genre(1,"Military prose"));

        NEW_BOOK = new Book(4,"New Book", new Author(5,"New Book Author"), new Genre(5,"New Book Genre"));
    }

    @Test
    @DisplayName("корректно ищет книги по названию")
    void shouldFindCorrect() {
        List<Book> books = bookService.findByNameContainingIgnoreCase("war");
        assertEquals(1, books.size());
        assertEquals(EXISTS_BOOK.getName(), bookService.findByNameContainingIgnoreCase("war").get(0).getName());
    }

    @Test
    @DisplayName("корректно добавляет новую книгу")
    void shouldAddCorrect(){
        assertEquals(3, bookService.findAll().size());
        assertEquals(NEW_BOOK, bookService.edit(0,"New Book","New Book Author","New Book Genre"));
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    @DisplayName("корректно читает комментарии к книге")
    void shouldFindCommentsCorrect(){
        Optional<Book> book = bookService.findById(EXISTS_BOOK.getId());
        assertEquals(2, bookService.getComments(book.get()).size());
    }
}