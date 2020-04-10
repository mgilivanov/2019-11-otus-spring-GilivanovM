package ru.otus.work12.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.work12.domain.Author;
import ru.otus.work12.domain.Book;
import ru.otus.work12.domain.Comment;
import ru.otus.work12.domain.Genre;
import ru.otus.work12.service.BookService;
import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "list_books";
    }

    @GetMapping("/delete")
    public RedirectView deleteBook(@RequestParam("id") String id, Model model) {
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
    public String editBookGet(@RequestParam("id") String id, Model model) {
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
    public String commentGet(@RequestParam("id") String id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isEmpty()) {
            model.addAttribute("book", book.get());
        }
        return "comment";
    }

    @PostMapping(value ="/comment")
    public RedirectView commentPost(@RequestParam("id") String id, Comment comment) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isEmpty()) {
            comment.setCreateDate(new Date());
           bookService.addComment(book.get(), comment);
        }
        return new RedirectView("comment?id="+id);
    }
}
