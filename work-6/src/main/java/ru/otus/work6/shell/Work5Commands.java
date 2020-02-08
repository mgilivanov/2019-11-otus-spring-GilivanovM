package ru.otus.work6.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Comment;
import ru.otus.work6.domain.Genre;
import ru.otus.work6.service.*;

import java.util.List;

@ShellComponent
public class Work5Commands {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final MessageService messageService;
    private final PrintService printService;

    public Work5Commands(BookService bookService, AuthorService authorService, GenreService genreService, CommentService commentService, MessageService messageService, PrintService printService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.messageService = messageService;
        this.printService = printService;
    }

    @ShellMethod(value = "List all books", key = {"list","list_books","books"})
    public void list() {
        List<Book> books = bookService.find("","","");
        printService.print(messageService.getMessage("list.books.message"));
        for (Book book : books) {
            printService.print(book);
        }
    }

    @ShellMethod(value = "List of all authors", key = {"list_authors","authors"})
    public void listAuthors() {
        List<Author> authors = authorService.list();
        printService.print(messageService.getMessage("list.authors.message"));
        for (Author author : authors) {
            printService.print(author);
        }
    }

    @ShellMethod(value = "List of all genres", key = {"list_genres","genres"})
    public void listGenres() {
        List<Genre> genres = genreService.list();
        printService.print(messageService.getMessage("list.genres.message"));
        for (Genre genre : genres) {
            printService.print(genre);
        }
    }

    @ShellMethod(value = "Find book by name", key = "find")
    public void find(@ShellOption(value = "--name", defaultValue="") String bookName,@ShellOption(value = "--author", defaultValue="") String authorName,@ShellOption(value = "--genre", defaultValue="") String genreName) {
        List<Book> books = bookService.find(bookName,authorName,genreName);
        printService.print(messageService.getMessage("list.books.message"));
        for (Book book : books) {
            printService.print(book);
        }
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
    public void del(long id) {
        if (bookService.deleteById(id)) {
            printService.print(messageService.getMessage("book.del.success", new String[]{String.valueOf(id)}));
        }
        else
        {
            printService.print(messageService.getMessage("book.del.failed", new String[]{String.valueOf(id)}));
        }
    }

    @ShellMethod(value = "Add comment by Book Id", key = {"add_comment"})
    public void addCommentByBookId(@ShellOption(value = "--id", help = "Books id")Long bookId,
                                   @ShellOption(value = "--text", help = "Comment for book")String text) {
        Comment newComment = commentService.add(bookId, text);
        if (newComment != null) {
            printService.print(messageService.getMessage("comment.add.success", new String[]{String.valueOf(newComment.getId())}));
        }
        else
        {
            printService.print(messageService.getMessage("comment.add.failed", new String[]{String.valueOf(bookId)}));
        }
    }

    @ShellMethod(value = "List of comments by Book Id", key = {"list_comment","comment"})
    public void ListCommentByBookId(@ShellOption(value = "--id", help = "Books id")Long bookId) {
        printService.print(messageService.getMessage("comment.list.message", new String[]{String.valueOf(bookId)}));
        List<Comment> comments = commentService.listByBookId(bookId);
        for (Comment comment : comments) {
            printService.print(comment.getId()+" : "+comment.getText());
        }
    }

}
