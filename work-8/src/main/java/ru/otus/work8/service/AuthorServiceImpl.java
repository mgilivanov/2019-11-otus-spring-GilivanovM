package ru.otus.work8.service;

import org.springframework.stereotype.Service;
import ru.otus.work8.domain.Author;
import ru.otus.work8.repository.AuthorRepository;

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
    public Optional<Author> findById(String id){
        return authorRepository.findById(id);
    }

    @Override
    public Author save(Author author){
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }
}
