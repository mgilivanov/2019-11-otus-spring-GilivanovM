package ru.mgilivanov.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "eods")
public class Eod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "open_time", nullable = false)
    private LocalDateTime openTime;

    @Column(name = "close_time_start", nullable = false)
    private LocalDateTime closeTimeStart;

    @Column(name = "close_time_end", nullable = false)
    private LocalDateTime closeTimeEnd;

    @Column(name = "status", nullable = false)
    private String status;

    @AllArgsConstructor
    @Getter
    public class Status{
        public static final String OPEN = "OPEN";
        public static final String CLOSING = "CLOSING";
        public static final String CLOSED = "CLOSED";
    }
}
