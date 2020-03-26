package ru.otus.work9;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work9.domain.Genre;
import ru.otus.work9.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Класс genreService")
@Transactional
public class GenreServiceTest {
    @Autowired
    private GenreService genreService;

    private final Genre EXISTS_GENRE;

    public GenreServiceTest(){
        EXISTS_GENRE = new Genre(1,"Tutorial");
    }

    @Test
    @DisplayName("корректно ищет существующий жанр")
    void shouldFindByNameCorrect() {
        assertEquals(EXISTS_GENRE, genreService.findByName(EXISTS_GENRE.getName()).get());
    }

    @Test
    @DisplayName("корректно ищет несуществующий жанр")
    void shouldFindByNameIncorrect() {
        assertEquals(Optional.empty(), genreService.findByName("False Genre"));
    }

}