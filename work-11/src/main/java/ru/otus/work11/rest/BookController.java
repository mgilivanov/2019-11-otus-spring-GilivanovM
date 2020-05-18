package ru.otus.work11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.work11.domain.*;
import ru.otus.work11.repository.BookRepository;

@RestController
@RequestMapping("/books_api/")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("list_books")
    public Flux<Book> listBooks() {
        return bookRepository.findAll();
    }

    @DeleteMapping("delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/edit")
    public Mono<Book> edit(@RequestBody Book book) {
        return bookRepository.save(book);
    }

}
