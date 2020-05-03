package ru.otus.work18.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work18.domain.Comment;

public interface CommentRepository  extends MongoRepository<Comment, Long> {
    Comment save(Comment comment);
}
