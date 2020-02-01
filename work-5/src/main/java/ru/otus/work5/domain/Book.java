package ru.otus.work5.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private long id;
    private final String name;
    private List<Author> authors;
    private List<Genre> genres;

    public Book(long id, String name) {
        this.id = id;
        this.name = name;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public Book(String name) {
        this.name = name;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setId(long id) {
        this.id = id;
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