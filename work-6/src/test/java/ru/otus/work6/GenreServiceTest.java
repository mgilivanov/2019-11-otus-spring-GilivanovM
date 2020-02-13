package ru.otus.work6;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Genre;
import ru.otus.work6.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
@DisplayName("Класс genreService")
@Transactional
public class GenreServiceTest {
    @Autowired
    private GenreService genreService;

    private final Genre EXISTS_GENRE;
    private final Genre NEW_GENRE;
    private final List<Genre> EXISTS_GENRES;

    public GenreServiceTest(){
        EXISTS_GENRE = new Genre(1,"Tutorial");
        EXISTS_GENRES = new ArrayList<>();
        EXISTS_GENRES.add(EXISTS_GENRE);
        NEW_GENRE = new Genre(5,"New Genre");
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