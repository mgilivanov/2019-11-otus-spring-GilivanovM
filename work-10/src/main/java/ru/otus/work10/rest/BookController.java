package ru.otus.work10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.work10.domain.*;
import ru.otus.work10.service.BookService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books_api/")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("list_books")
    public List<BookDto> listBooks() {
        return bookService.findAll().stream().map(book -> new BookDto(book)).collect(Collectors.toList());
    }

    @PostMapping("delete/{id}")
    public void delete(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @PostMapping("/edit")
    public void edit(@RequestBody BookDto bookDto) {
        bookService.edit(bookDto.getId(), bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
    }

    @GetMapping("list_comments/{id}")
    public List<Comment> listComments(@PathVariable String id) {
        return bookService.getComments(bookService.findById(id).get());
    }

    @PostMapping("/add_comment")
    public void addComment(@RequestBody CommentDTO comment) {
        Book book = bookService.findById(comment.getBookId()).get();
        Comment newComment = new Comment(comment.getText(), new Date());
        bookService.addComment(book, newComment);
    }

}
