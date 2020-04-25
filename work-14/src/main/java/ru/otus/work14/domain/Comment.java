package ru.otus.work14.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String text;
    private Date createDate;
    @DBRef
    private Book book;
}
