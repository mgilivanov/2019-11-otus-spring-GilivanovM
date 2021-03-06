package ru.otus.work18.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work18.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findByAuthorNameContainingIgnoreCase(String name);

    List<Book> findByGenreNameContainingIgnoreCase(String name);

    List<Book> findAll();

    Book save(Book book);

    Optional<Book> findById(String id);

    void deleteById(String id);
}
