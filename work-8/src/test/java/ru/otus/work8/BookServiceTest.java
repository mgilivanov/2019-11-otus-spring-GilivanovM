package ru.otus.work8;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.work8.domain.Author;
import ru.otus.work8.domain.Book;
import ru.otus.work8.domain.Genre;
import ru.otus.work8.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс BookService")
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    private final String NEW_BOOK_NAME;
    private final String NEW_AUTHOR_NAME;
    private final String NEW_GENRES_NAME;
    private final String COMMENT_TEXT_FIRST;
    private final String COMMENT_TEXT_SECOND;
    private final Book EXISTS_BOOK;

    public BookServiceTest(){
        NEW_BOOK_NAME = "New Book";
        NEW_AUTHOR_NAME = "author1";
        NEW_GENRES_NAME = "genre1,genre2";
        COMMENT_TEXT_FIRST = "comment1";
        COMMENT_TEXT_SECOND = "comment2";
        EXISTS_BOOK = new Book("1","War and Peace");
        EXISTS_BOOK.addAuthor(new Author("1", "Lev Tolstoy"));
        EXISTS_BOOK.addGenre(new Genre("1","Novel"));
        EXISTS_BOOK.addGenre(new Genre("2","Military Prose"));
    }

    @Test
    @DisplayName("корректно ищет книги по названию")
    void shouldFindCorrect() {
        assertEquals(EXISTS_BOOK.getName(), bookService.findByNameContainingIgnoreCase("war").get(0).getName());
    }

    @Test
    @DisplayName("корректно добавляет новую книгу")
    void shouldAddCorrect(){
        int booksCount;
        booksCount = bookService.findAll().size();
        assertEquals(NEW_BOOK_NAME, bookService.add(NEW_BOOK_NAME,NEW_AUTHOR_NAME,NEW_GENRES_NAME).getName());
        assertEquals(booksCount + 1, bookService.findAll().size());
    }

    @Test
    @DisplayName("корректно читает комментарии к книге")
    void shouldFindCommentsCorrect(){
        bookService.addComment(EXISTS_BOOK, COMMENT_TEXT_FIRST);
        bookService.addComment(EXISTS_BOOK, COMMENT_TEXT_SECOND);
        assertEquals(2, bookService.getComments(EXISTS_BOOK).size());
    }
}