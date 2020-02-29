package ru.otus.work8.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;

    @DBRef
    @Indexed
    private List<Author> authors;
    @DBRef
    @Indexed
    private List<Genre> genres = new ArrayList<>();
    private List<Comment> comments;

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Book(String name) {
        this.name = name;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.comments = new ArrayList<>();
    }


    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(name, book.name) &&
                Objects.equals(authors, book.authors) &&
                Objects.equals(genres, book.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authors, genres);
    }
}