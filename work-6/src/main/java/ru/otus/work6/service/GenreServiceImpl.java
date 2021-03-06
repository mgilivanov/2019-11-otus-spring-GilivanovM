package ru.otus.work6.service;

import org.springframework.stereotype.Service;
import ru.otus.work6.dao.GenreDao;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Genre;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> findByName(String name){
        return genreDao.findByName(name);
    }

    @Override
    public Optional<Genre> findById(long id){
        return genreDao.findById(id);
    }

    @Override
    public Genre create(String name){
        Genre genre = new Genre(name);
        genreDao.update(genre);
        return genre;
    }

    @Override
    public List<Genre> list(){
        return genreDao.list();
    }
}
