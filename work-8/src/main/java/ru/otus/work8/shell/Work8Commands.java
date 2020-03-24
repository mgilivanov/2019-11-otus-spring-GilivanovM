package ru.otus.work8.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work8.domain.Author;
import ru.otus.work8.domain.Book;
import ru.otus.work8.domain.Comment;
import ru.otus.work8.domain.Genre;
import ru.otus.work8.service.*;

import java.util.List;
import java.util.Optional;

@ShellComponent
@Transactional
public class Work8Commands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final MessageService messageService;
    private final PrintService printService;

    public Work8Commands(BookService bookService, AuthorService authorService, GenreService genreService, MessageService messageService, PrintService printService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.messageService = messageService;
        this.printService = printService;
    }

    @ShellMethod(value = "List all books", key = {"list","list_books","books"})
    public void list() {
        List<Book> books = bookService.findAll();
        printService.print(messageService.getMessage("list.books.message"));
        printService.print(books);
    }

    @ShellMethod(value = "List of all authors", key = {"list_authors","authors"})
    public void listAuthors() {
        List<Author> authors = authorService.findAll();
        printService.print(messageService.getMessage("list.authors.message"));
        printService.print(authors);
    }

    @ShellMethod(value = "List of all genres", key = {"list_genres","genres"})
    public void listGenres() {
        List<Genre> genres = genreService.findAll();
        printService.print(messageService.getMessage("list.genres.message"));
        printService.print(genres);
    }

    @ShellMethod(value = "Find book by name", key = "find")
    public void find(@ShellOption(value = "--name", defaultValue="") String bookName) {
        List<Book> books = bookService.findByNameContainingIgnoreCase(bookName);
        printService.print(messageService.getMessage("list.books.message"));
        printService.print(books);
    }

    @ShellMethod(value = "Find book by author id", key = "find_by_author")
    public void findByAuthorId(@ShellOption(value = "--id", defaultValue="") String authorId) {
        Optional<Author> author = authorService.findById(authorId);
        printService.print(messageService.getMessage("list.books.message"));
        printService.print(bookService.findAllByAuthorsId(authorId));
    }

    @ShellMethod(value = "Find book by genre name", key = "find_by_genre")
    public void findByGenreId(@ShellOption(value = "--id", defaultValue="") String genreId) {
        printService.print(messageService.getMessage("list.books.message"));
        printService.print(bookService.findAllByGenresId(genreId));
    }

    @ShellMethod(value = "Add book to the library", key = "add")
    public void add(@ShellOption(value = "--name")String name,
                    @ShellOption(value = "--authors", defaultValue = "", help = "List the authors separated by commas. Sample: author1,author2,...")String authorsStr,
                    @ShellOption(value = "--genres", defaultValue = "", help = "List the genres separated by commas. Sample: genre1,genre2,...")String genresStr) {
        Book newBook =bookService.add(name, authorsStr, genresStr);
        if (newBook != null) {
            printService.print(messageService.getMessage("book.add.success", new String[]{name, authorsStr, genresStr, String.valueOf(newBook.getId())}));
        }
        else
        {
            printService.print(messageService.getMessage("book.add.failed", new String[]{name, authorsStr, genresStr}));
        }
    }

    @ShellMethod(value = "Delete book by id", key = {"del","delete","erase"})
    public void del(String id) {
        bookService.deleteById(id);
        printService.print(messageService.getMessage("book.del.success", new String[]{String.valueOf(id)}));
    }

    @ShellMethod(value = "Add comment by Book Id", key = {"add_comment"})
    public void addCommentByBookId(@ShellOption(value = "--id", help = "Books id")String bookId,
                                   @ShellOption(value = "--text", help = "Comment for book")String text) {
        Optional<Book> book = bookService.findById(bookId);
        if (!book.isEmpty()){
            Comment newComment = bookService.addComment(book.get(), text);
            printService.print(messageService.getMessage("comment.add.success", new String[]{String.valueOf(newComment.getText())}));
        }
        else
        {
            printService.print(messageService.getMessage("comment.add.failed", new String[]{String.valueOf(bookId)}));
        }
    }

    @ShellMethod(value = "List of comments by Book Id", key = {"list_comment","comment"})
    public void ListCommentByBookId(@ShellOption(value = "--id", help = "Books id")String bookId) {
        printService.print(messageService.getMessage("comment.list.message", new String[]{String.valueOf(bookId)}));
        Optional<Book> book = bookService.findById(bookId);
        if (! book.isEmpty()){
            printService.print(book.get().getComments());
        }
    }

}
