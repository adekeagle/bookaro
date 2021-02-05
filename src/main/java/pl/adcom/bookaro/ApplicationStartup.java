package pl.adcom.bookaro;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adcom.bookaro.catalog.application.CatalogController;
import pl.adcom.bookaro.domain.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;

    public ApplicationStartup(CatalogController catalogController,@Value("bookaro.catalog.query") String title) {
        this.catalogController = catalogController;
        this.title = title;
    }

    //    public ApplicationStartup(CatalogController catalogController) {
//        this.catalogController = catalogController;
//    }

    @Override
    public void run(String... args) throws Exception {
//		CatalogService service = new CatalogService();
        List<Book> books = catalogController.findByTitle(title);
        books.forEach(System.out::println);
    }
}
