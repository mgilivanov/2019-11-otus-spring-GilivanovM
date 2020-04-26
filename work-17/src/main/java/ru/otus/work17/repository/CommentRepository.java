package ru.otus.work17.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work17.domain.Comment;

public interface CommentRepository  extends MongoRepository<Comment, Long> {
    Comment save(Comment comment);
}
