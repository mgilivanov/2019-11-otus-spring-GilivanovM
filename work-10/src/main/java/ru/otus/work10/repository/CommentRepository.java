package ru.otus.work10.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.work10.domain.Comment;

public interface CommentRepository  extends MongoRepository<Comment, Long> {
    Comment save(Comment comment);
}
