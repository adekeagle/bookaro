package pl.adcom.bookaro.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.adcom.bookaro.catalog.domain.Book;
import pl.adcom.bookaro.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong(0L);

//    public MemoryCatalogRepository() {
//        storage.put(1L, new Book(1L, "Harry Potter i Komnata Tajemnic", "JK Rowling", 1998));
//        storage.put(2L, new Book(2L, "Władca Pierścieni: Dwie wieże", "JRR Tolkien", 1954));
//        storage.put(3L, new Book(3L, "Mężczyźni, którzy nienawidzą kobiet", "Stieg Larsson", 2005));
//        storage.put(4L, new Book(4L, "Sezon Burz", "Andrzej Sapkowski", 2013));
//    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Book> findByAuthor() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Book save(Book book) {
        if(book.getId() != null){
            storage.put(book.getId(), book);
        }else{
            long nextId = nextId();
            book.setId(nextId);
            storage.put(nextId, book);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void removeById(Long id) {
        storage.remove(id);
    }

    private long nextId(){
        return ID_NEXT_VALUE.getAndIncrement();
    }
}
