package ru.otus.work10.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CommentDTO {
    private String text;
    private String bookId;
}
