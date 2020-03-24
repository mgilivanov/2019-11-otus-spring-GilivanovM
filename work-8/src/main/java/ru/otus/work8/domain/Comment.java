package ru.otus.work8.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    String text;

    @Override
    public String toString() {
        return "Comment{"+
                "text='" + text + '\'' +
                '}';
    }

}
