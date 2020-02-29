package ru.otus.work8.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;
    private String name;
    @DBRef
    @Indexed
    private List<Book> books;

    public Genre (String name){
        this.name = name;
    }

    public Genre (String id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
