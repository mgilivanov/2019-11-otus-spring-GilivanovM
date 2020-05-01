package ru.otus.work16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.work16.domain.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "books")
public interface BookRepository extends MongoRepository<Book, Long> {
    @RestResource(path = "name", rel = "name")
    List<Book> findByNameContainingIgnoreCase(String name);

    @RestResource(path = "all", rel = "all")
    List<Book> findAll();

    @RestResource(exported = false)
    Book save(Book book);

    @RestResource(path = "id", rel = "id")
    Optional<Book> findById(String id);

    @RestResource(exported = false)
    void deleteById(String id);
}
