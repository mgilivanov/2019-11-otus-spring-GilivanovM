package ru.otus.work16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.work16.domain.Comment;
@RepositoryRestResource(exported = false)
public interface CommentRepository  extends MongoRepository<Comment, Long> {
    Comment save(Comment comment);
}
