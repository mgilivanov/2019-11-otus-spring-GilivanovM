package ru.otus.work10.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Comment {
    private String text;
    private Date createDate;
}
