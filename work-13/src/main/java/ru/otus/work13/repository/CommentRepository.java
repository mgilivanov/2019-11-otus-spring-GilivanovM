package ru.otus.work13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work13.domain.Comment;

public interface CommentRepository  extends MongoRepository<Comment, Long> {
    Comment save(Comment comment);
}
