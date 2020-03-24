package ru.otus.work8;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work8.domain.Author;
import ru.otus.work8.domain.Genre;
import ru.otus.work8.service.AuthorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс AuthorService")
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    private final Author EXISTS_AUTHOR;
    private final Author NEW_AUTHOR;
    private final List<Author> EXISTS_AUTHORS;

    public AuthorServiceTest(){
        EXISTS_AUTHOR = new Author("1","Lev Tolstoy");
        EXISTS_AUTHORS = new ArrayList<>();
        EXISTS_AUTHORS.add(EXISTS_AUTHOR);
        NEW_AUTHOR = new Author("5","New Author");
    }

    @Test
    @DisplayName("корректно ищет существующего автора")
    void shouldFindByNameCorrect() {
        assertEquals(EXISTS_AUTHOR, authorService.findByName(EXISTS_AUTHOR.getName()).get());
    }

    @Test
    @DisplayName("корректно ищет несуществующего автора")
    void shouldFindByNameIncorrect() {
        assertEquals(Optional.empty(), authorService.findByName("False Author"));
    }

}