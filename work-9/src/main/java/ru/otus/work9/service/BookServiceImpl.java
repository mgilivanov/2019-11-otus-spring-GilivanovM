package ru.otus.work9.service;

import org.springframework.stereotype.Service;
import ru.otus.work9.domain.Author;
import ru.otus.work9.domain.Book;
import ru.otus.work9.domain.Comment;
import ru.otus.work9.domain.Genre;
import ru.otus.work9.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
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
    public Book edit(long id, String name, String authorName, String genreName) {
        Book book = new Book(id, name);
        Optional<Author> author = authorService.findByName(authorName);
        if (author.isEmpty()) {
            book.setAuthor(authorService.create(authorName));
        }
        else{
            book.setAuthor(author.get());
        }
        Optional<Genre> genre = genreService.findByName(genreName);
        if (genre.isEmpty()) {
            book.setGenre(genreService.create(genreName));
        }
        else{
            book.setGenre(genre.get());
        }

        bookRepository.save(book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    public List<Comment> getComments(Book book){
        return book.getComments();
    }

}
