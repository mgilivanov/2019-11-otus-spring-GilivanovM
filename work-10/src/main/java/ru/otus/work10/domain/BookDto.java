package ru.otus.work10.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private String genre;
    private String author;

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.genre = book.getGenre().getName();
        this.author = book.getAuthor().getName();
    }

    public Book toBook(){
        return new Book(this.id,this.name,new Author(this.author),new Genre(this.genre));
    }
}
