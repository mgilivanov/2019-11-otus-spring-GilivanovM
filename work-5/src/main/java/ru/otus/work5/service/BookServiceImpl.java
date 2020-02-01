package ru.otus.work5.service;

import org.springframework.stereotype.Service;
import ru.otus.work5.dao.BookDao;
import ru.otus.work5.domain.Author;
import ru.otus.work5.domain.Book;
import ru.otus.work5.domain.Genre;

import java.util.List;

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
    public List<Book> find(String bookName, String authorName, String genreName) {
        List<Book> books = bookDao.find(bookName, authorName, genreName);
        return books;
    }

    @Override
    public Book add(String name, String authorsStr, String genresStr) {
        Book book = new Book(name);
        String[] authorName = authorsStr.split(",");
        for(int i = 0; i < authorName.length; i++) {
            Author author = authorService.findByName(authorName[i]);
            if (author == null) {
                author = authorService.add(authorName[i]);
            }
            book.addAuthor(author);
        }
        String[] genreName = genresStr.split(",");
        for(int i = 0; i < genreName.length; i++) {
            Genre genre = genreService.findByName(genreName[i]);
            if (genre == null) {
                genre = genreService.add(genreName[i]);
            }
            book.addGenre(genre);
        }

        bookDao.add(book);
        return book;
    }

    @Override
    public boolean deleteById(long id) {
        return bookDao.deleteById(id);
    }

}
