package pl.adcom.bookaro.infrastructure;

import org.springframework.stereotype.Repository;
import pl.adcom.bookaro.domain.Book;
import pl.adcom.bookaro.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BestsellerCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestsellerCatalogRepository() {
        storage.put(1L, new Book(1L, "Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
        storage.put(2L, new Book(2L, "Władca Pierścieni: Dwie wieże", "JRR Tolkien", 1954));
        storage.put(3L, new Book(3L, "Mężczyźni, którzy nienawidzą kobiet", "Stieg Larsson", 2005));
        storage.put(4L, new Book(4L, "Sezon Burz", "Andrzej Sapkowski", 2013));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Book> findByAuthor() {
        return new ArrayList<>(storage.values());
    }

}
