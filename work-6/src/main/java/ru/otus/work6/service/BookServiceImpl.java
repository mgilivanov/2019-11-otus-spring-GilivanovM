package ru.otus.work6.service;

import org.springframework.stereotype.Service;
import ru.otus.work6.dao.AuthorDao;
import ru.otus.work6.dao.BookDao;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Comment;
import ru.otus.work6.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public List<Book> findByName(String bookName) {
        List<Book> books = bookDao.findByName(bookName);
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookDao.findAll();
        return books;
    }

    @Override
    public Book add(String name, String authorsStr, String genresStr) {
        Book book = new Book(name);
        String[] authorName = authorsStr.split(",");
        for(int i = 0; i < authorName.length; i++) {
            Optional<Author> author = authorService.findByName(authorName[i]);
            if (author.isEmpty()) {
                book.addAuthor(authorService.create(authorName[i]));
            }
            else{
                book.addAuthor(author.get());
            }

        }
        String[] genreName = genresStr.split(",");
        for(int i = 0; i < genreName.length; i++) {
            Optional<Genre> genre = genreService.findByName(genreName[i]);
            if (genre.isEmpty()) {
                book.addGenre(genreService.create(genreName[i]));
            }
            else{
                book.addGenre(genre.get());
            }
        }

        bookDao.add(book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id){
        return bookDao.findById(id);
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id);
    }

    public List<Comment> getComments(Book book){
        return book.getComments();
    }

}
