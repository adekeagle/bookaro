package pl.adcom.bookaro.domain;

import lombok.*;

import java.util.StringJoiner;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Book {

    private final Long Id;
    private final String title;
    private final String author;
    private final Integer year;

}
