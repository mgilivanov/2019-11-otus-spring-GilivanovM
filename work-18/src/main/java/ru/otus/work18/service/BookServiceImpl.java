package ru.otus.work18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import ru.otus.work18.domain.Author;
import ru.otus.work18.domain.Book;
import ru.otus.work18.domain.Comment;
import ru.otus.work18.domain.Genre;
import ru.otus.work18.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    }, fallbackMethod = "findAllFallback")
    public List<Book> findAll()  {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public List<Book> findAllFallback() {
        List<Book> books = new ArrayList();
        return books;
    }

    @Override
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public Book edit(String id, String name, String authorName, String genreName) {
        Book book = new Book(id, name, new Author(authorName), new Genre(genreName));
        bookRepository.save(book);
        return book;
    }

    @Override
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }, fallbackMethod = "findByIdFallback")
    public Optional<Book> findById(String id){
        return bookRepository.findById(id);
    }

    public Optional<Book> findByIdFallback(String id){
        return Optional.empty();
    }

    @Override
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<Comment> getComments(Book book){
        return book.getComments();
    }

    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public void addComment(Book book, Comment comment){
        book.addComment(comment);
        bookRepository.save(book);
    }

}
