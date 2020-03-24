package ru.otus.work8.service;

import org.springframework.stereotype.Service;
import ru.otus.work8.domain.Genre;
import ru.otus.work8.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }
    public List<Genre> findAll(){
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findByName(String name){
        return genreRepository.findByName(name);
    }
}
