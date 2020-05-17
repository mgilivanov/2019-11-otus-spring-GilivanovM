package ru.otus.work10;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.work10.domain.Author;
import ru.otus.work10.domain.Book;
import ru.otus.work10.domain.Comment;
import ru.otus.work10.domain.Genre;
import ru.otus.work10.service.BookService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("Класс BookService")
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    private final Book EXISTS_BOOK;
    private final Book NEW_BOOK;

    public BookServiceTest(){
        EXISTS_BOOK = new Book("1","War and Peace", new Author("Lev Tolstoy"), new Genre("Military prose"));

        NEW_BOOK = new Book("4","New Book", new Author("New Book Author"), new Genre("New Book Genre"));
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
        assertEquals(1, bookService.findAll().size());
        bookService.edit(null, "New Book","New Book Author","New Book Genre");
        assertEquals(2, bookService.findAll().size());
    }

    @Test
    @DisplayName("корректно добавляет комментарии к книге")
    void shouldFindCommentsCorrect(){
        Optional<Book> book = bookService.findById(EXISTS_BOOK.getId());
        bookService.addComment(book.get(), new Comment("test comment", new Date()));
        assertEquals(3, bookService.getComments(book.get()).size());
    }
}