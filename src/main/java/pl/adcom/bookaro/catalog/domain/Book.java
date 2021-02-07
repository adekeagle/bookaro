package pl.adcom.bookaro.catalog.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.StringJoiner;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Book {

    private Long Id;
    private String title;
    private String author;
    private Integer year;
    private BigDecimal price;

    public Book(String title, String author, Integer year, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}
