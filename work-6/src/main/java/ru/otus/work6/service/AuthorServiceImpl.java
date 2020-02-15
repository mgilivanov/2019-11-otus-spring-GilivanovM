package ru.otus.work6.service;

import org.springframework.stereotype.Service;
import ru.otus.work6.dao.AuthorDao;
import ru.otus.work6.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> findByName(String name){
        return authorDao.findByName(name);
    }

    @Override
    public Optional<Author> findById(long id){
        return authorDao.findById(id);
    }

    @Override
    public Author create(String name){
        Author author = new Author(name);
        authorDao.update(author);
        return author;
    }

    @Override
    public List<Author> list(){
        return authorDao.list();
    }
}
