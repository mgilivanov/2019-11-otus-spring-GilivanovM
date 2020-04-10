package ru.otus.work9.service;

import org.springframework.stereotype.Service;
import ru.otus.work9.domain.Book;
import ru.otus.work9.domain.Comment;
import ru.otus.work9.repository.CommentRepository;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Override
    public Comment add(Long BookId, String text) {
        Optional<Book> book = bookService.findById(BookId);
        if (book.isEmpty()){
            return null;
        }
        else {
            Comment comment = new Comment(book.get(), text);
            commentRepository.save(comment);
            return comment;
        }
    }
}
