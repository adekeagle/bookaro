package pl.adcom.bookaro;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService(){
        storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1876));
        storage.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
        storage.put(3L, new Book(3L, "Chłopi", "Władysław Reymont", 1904));

    }

    List<Book> findByTitle(String title){
        return storage.values()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}