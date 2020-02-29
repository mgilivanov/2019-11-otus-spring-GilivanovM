package ru.otus.work8.service;

import org.springframework.stereotype.Service;
import ru.otus.work8.domain.Author;
import ru.otus.work8.domain.Book;
import ru.otus.work8.domain.Comment;
import ru.otus.work8.domain.Genre;
import ru.otus.work8.repository.BookRepository;

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
    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Book add(String name, String authorsStr, String genresStr) {
        Book book = new Book(name);
        String[] authorName = authorsStr.split(",");
        for(int i = 0; i < authorName.length; i++) {
            Optional<Author> author = authorService.findByName(authorName[i]);
            if (author.isEmpty()) {
                book.addAuthor(authorService.save(new Author(authorName[i])));
            }
            else{
                book.addAuthor(author.get());
            }
        }
        String[] genreName = genresStr.split(",");
        for(int i = 0; i < genreName.length; i++) {
                Optional<Genre> genre = genreService.findByName(genreName[i]);
            if (genre.isEmpty()) {
                book.addGenre(genreService.save(new Genre(genreName[i])));
            }
            else{
                book.addGenre(genre.get());
            }
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

    public List<Book> findAllByGenresId(String genreId){
        return bookRepository.findAllByGenresId(genreId);
    }

    public List<Book> findAllByAuthorsId(String authorId){
        return bookRepository.findAllByAuthorsId(authorId);
    }

    public Book save(Book book){
        return bookRepository.save(book);
    };

    public Comment addComment(Book book, String text){
        Comment newComment = new Comment(text);
        book.addComment(newComment);
        save(book);
        return  newComment;
    }

}
