package pl.adcom.bookaro.domain;

import java.util.List;

public interface CatalogRepository {

    List<Book> findAll();
    List<Book> findByAuthor();
}
