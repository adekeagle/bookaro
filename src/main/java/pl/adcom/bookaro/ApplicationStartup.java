package pl.adcom.bookaro;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adcom.bookaro.catalog.application.CatalogController;
import pl.adcom.bookaro.domain.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

//    public ApplicationStartup(CatalogController catalogController) {
//        this.catalogController = catalogController;
//    }

    @Override
    public void run(String... args) throws Exception {
//		CatalogService service = new CatalogService();
        List<Book> books = catalogController.findByTitle("Pan");
        books.forEach(System.out::println);
    }
}
