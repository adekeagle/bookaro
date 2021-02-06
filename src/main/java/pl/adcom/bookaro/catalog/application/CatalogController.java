package pl.adcom.bookaro.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.adcom.bookaro.domain.Book;
import pl.adcom.bookaro.domain.CatalogService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService service;

//    public CatalogController(CatalogService service) {
//        this.service = service;
//    }

    public List<Book> findByTitle(String title){
        return service.findByTitle(title);
    }

    public List<Book> findByAuthor(String author){
        return service.findByAuthor(author);
    }
}
