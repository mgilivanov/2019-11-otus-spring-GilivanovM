package ru.otus.work11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.work11.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, Long> {
    Flux<Book> findAll();

    Mono<Book> findById(String id);

    Mono<Void> deleteById(String id);
}
