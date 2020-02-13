package ru.otus.work6.service;

import org.springframework.stereotype.Service;
import ru.otus.work6.dao.CommentDao;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Comment;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookService bookService;

    public CommentServiceImpl(CommentDao commentDao, BookService bookService) {
        this.commentDao = commentDao;
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
            commentDao.add(comment);
            return comment;
        }
    }
}
