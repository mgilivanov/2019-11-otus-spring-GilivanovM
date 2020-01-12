package ru.otus.work5;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work5.domain.Author;
import ru.otus.work5.service.AuthorService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс AuthorService")
@Transactional
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    private final Author EXISTS_AUTHOR;
    private final Author NEW_AUTHOR;
    private final List<Author> EXISTS_AUTHORS;

    public AuthorServiceTest(){
        EXISTS_AUTHOR = new Author(1,"Lev Tolstoy");
        EXISTS_AUTHORS = new ArrayList<>();
        EXISTS_AUTHORS.add(EXISTS_AUTHOR);
        NEW_AUTHOR = new Author(5,"New Author");
    }

    @Test
    @DisplayName("корректно ищет существующего автора")
    void shouldFindByNameCorrect() {
        assertEquals(EXISTS_AUTHOR, authorService.findByName(EXISTS_AUTHOR.getName()));
    }

    @Test
    @DisplayName("корректно ищет несуществующего автора")
    void shouldFindByNameIncorrect() {
        assertEquals(null, authorService.findByName("False Author"));
    }

    @Test
    @DisplayName("корректно ищет автора по названию книги")
    void shouldFindByBookIdCorrect() {
        assertEquals(EXISTS_AUTHORS, authorService.findByBookId(1));
    }

    @Test
    @DisplayName("корректно добавляет нового автора")
    void shouldAddCorrect() {
        assertEquals(4, authorService.list().size());
        assertEquals(NEW_AUTHOR, authorService.add(NEW_AUTHOR.getName()));
        assertEquals(5, authorService.list().size());
    }


}