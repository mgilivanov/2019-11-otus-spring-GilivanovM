package ru.otus.work5.service;

import org.springframework.stereotype.Service;
import ru.otus.work5.dao.AuthorDao;
import ru.otus.work5.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> findByBookId(long id) {
        return authorDao.findByBookId(id);
    }

    @Override
    public Author findByName(String name){
        return authorDao.findByName(name);
    }

    @Override
    public Author add(String name){
        Author author = new Author(name);
        authorDao.add(author);
        return author;
    }

    @Override
    public List<Author> list(){
        return authorDao.list();
    }
}
