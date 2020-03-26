package ru.otus.work9.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.work9.domain.Author;
import ru.otus.work9.domain.Book;
import ru.otus.work9.domain.Comment;
import ru.otus.work9.domain.Genre;
import ru.otus.work9.service.BookService;
import ru.otus.work9.service.CommentService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "list_books";
    }

    @GetMapping("/delete")
    public RedirectView deleteBook(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        bookService.deleteById(id);
        return new RedirectView("/");
    }

    @GetMapping("/edit")
    public String addBook(Model model) {
        model.addAttribute("book", new Book(null, new Author(""), new Genre("")));
        return "edit";
    }

    @GetMapping(value ="/edit", params = "id")
    public String editBookGet(@RequestParam("id") long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isEmpty()) {
            model.addAttribute("book", book.get());
        }
        return "edit";
    }

    @PostMapping("/edit")
    public RedirectView editBookPost(Book book) {
        bookService.edit(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        return new RedirectView("/");
    }

    @GetMapping(value ="/comment", params = "id")
    public String commentGet(@RequestParam("id") long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isEmpty()) {
            model.addAttribute("book", book.get());
        }
        return "comment";
    }

    @PostMapping(value ="/comment")
    public RedirectView commentPost(@RequestParam("id") long id, Comment comment) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isEmpty()) {
            commentService.add(id, comment.getText());
        }
        return new RedirectView("comment?id="+id);
    }
}
