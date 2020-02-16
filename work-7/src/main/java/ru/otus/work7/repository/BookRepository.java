package ru.otus.work7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.work7.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByNameContainingIgnoreCase(String name);

    List<Book> findAll();

    Book save(Book book);

    Optional<Book> findById(Long id);

    void deleteById(Long id);
}
