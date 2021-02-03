package pl.adcom.bookaro;

import java.util.StringJoiner;

public class Book {

    private Long Id;
    private String title;
    private String author;
    private Integer year;

    public Book(Long id, String title, String author, Integer year) {
        Id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("Id=" + Id)
                .add("title='" + title + "'")
                .add("author='" + author + "'")
                .add("year=" + year)
                .toString();
    }
}
