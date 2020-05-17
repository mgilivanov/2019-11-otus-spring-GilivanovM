package ru.otus.work10.service;

import org.springframework.stereotype.Service;
import ru.otus.work10.domain.Author;
import ru.otus.work10.domain.Book;
import ru.otus.work10.domain.Comment;
import ru.otus.work10.domain.Genre;
import ru.otus.work10.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findByNameContainingIgnoreCase(String name) {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase(name);
        return books;
    }

    @Override
    public List<Book> findByAuthorNameContainingIgnoreCase(String name) {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase(name);
        return books;
    }

    @Override
    public List<Book> findByGenreNameContainingIgnoreCase(String name) {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase(name);
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Book edit(String id, String name, String authorName, String genreName) {
        Book book;
        if (id == null){
            book = new Book(id, name, new Author(authorName), new Genre(genreName));
        }
        else
        {
            book = findById(id).get();
            book.setName(name);
            book.setAuthor(new Author(authorName));
            book.setGenre(new Genre(genreName));
        }
        bookRepository.save(book);
        return book;
    }

    @Override
    public Optional<Book> findById(String id){
        return bookRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<Comment> getComments(Book book){
        return book.getComments();
    }

    public void addComment(Book book, Comment comment){
        book.addComment(comment);
        bookRepository.save(book);
    }

}
