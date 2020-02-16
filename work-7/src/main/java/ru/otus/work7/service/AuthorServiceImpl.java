package ru.otus.work7.service;

import org.springframework.stereotype.Service;
import ru.otus.work7.domain.Author;
import ru.otus.work7.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> findByName(String name){
        return authorRepository.findByName(name);
    }

    @Override
    public Optional<Author> findById(long id){
        return authorRepository.findById(id);
    }

    @Override
    public Author create(String name){
        Author author = new Author(name);
        authorRepository.save(author);
        return author;
    }

    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }
}
