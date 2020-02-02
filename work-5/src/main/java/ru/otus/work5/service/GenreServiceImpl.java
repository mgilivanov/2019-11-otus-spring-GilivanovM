package ru.otus.work5.service;

import org.springframework.stereotype.Service;
import ru.otus.work5.dao.GenreDao;
import ru.otus.work5.domain.Genre;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> findByBookId(long id) {
        return genreDao.findByBookId(id);
    }

    @Override
    public Genre findByName(String name){
        return genreDao.findByName(name);
    }

    @Override
    public Genre add(String name){
        Genre genre = new Genre(name);
        genreDao.add(genre);
        return genre;
    }

    @Override
    public List<Genre> list(){
        return genreDao.list();
    }
}
