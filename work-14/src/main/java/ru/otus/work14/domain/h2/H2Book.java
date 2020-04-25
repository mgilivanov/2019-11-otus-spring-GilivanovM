package ru.otus.work14.domain.h2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class H2Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = H2Author.class,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "author_id", nullable = false)
    private H2Author author;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = H2Genre.class,  cascade={CascadeType.MERGE})
    @JoinColumn(name = "genre_id", nullable = false)
    private H2Genre genre;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "book")
    private List<H2Comment> comments;

    public H2Book(String name, H2Author author, H2Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public H2Book(String name) {
        this.name = name;
    }
}