package ru.otus.work18.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;
    private Author author;
    private Genre genre;
    private List<Comment> comments;

    public Book(String id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Book(String name) {
        this.name = name;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}