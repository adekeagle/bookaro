package pl.adcom.bookaro.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.adcom.bookaro.domain.Book;
import pl.adcom.bookaro.infrastructure.SchoolCatalogRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository repository;
//    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService(@Qualifier("schoolCatalogRepository") CatalogRepository repository) {
        this.repository = repository;
    }


//    @Autowired
//    public CatalogService(CatalogRepository repository){
//        this.repository = repository;
////        storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1876));
////        storage.put(2L, new Book(2L, "Ogniem i Mieczem", "Henryk Sienkiewicz", 1884));
////        storage.put(3L, new Book(3L, "Chłopi", "Władysław Reymont", 1904));
//
//    }

    public List<Book> findByTitle(String title){
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
