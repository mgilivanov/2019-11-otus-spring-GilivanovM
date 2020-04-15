package ru.otus.work14.domain.h2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class H2Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = H2Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    H2Book book;

    @Column(name = "text", nullable = false)
    String text;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    public H2Comment (H2Book book, String text, Date createDate){
        this.book = book;
        this.text = text;
        this.createDate = createDate;
    }

}
