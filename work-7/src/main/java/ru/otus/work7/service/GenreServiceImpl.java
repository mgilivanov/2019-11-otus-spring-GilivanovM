package ru.otus.work7.service;

import org.springframework.stereotype.Service;
import ru.otus.work7.domain.Genre;
import ru.otus.work7.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<Genre> findByName(String name){
        return genreRepository.findByName(name);
    }

    @Override
    public Optional<Genre> findById(long id){
        return genreRepository.findById(id);
    }

    @Override
    public Genre create(String name){
        Genre genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public List<Genre> findAll(){
        return genreRepository.findAll();
    }
}
