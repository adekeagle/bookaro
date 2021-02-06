package pl.adcom.bookaro;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adcom.bookaro.catalog.application.CatalogController;
import pl.adcom.bookaro.domain.Book;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(CatalogController catalogController,
                              @Value("${bookaro.catalog.query}") String title,
                              @Value("${bookaro.catalog.limit:3}") Long limit,
                              @Value("${bookaro.catalog.author}") String author) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    //    public ApplicationStartup(CatalogController catalogController) {
//        this.catalogController = catalogController;
//    }

    @Override
    public void run(String... args) throws Exception {
//		CatalogService service = new CatalogService();
        List<Book> books = catalogController.findByTitle(title);
//        books.forEach(System.out::println);
        books.stream().limit(limit).forEach(System.out::println);

        List<Book> booksFindedByauthor = catalogController.findByAuthor("Andrzej");
        booksFindedByauthor.stream().forEach(System.out::println);
    }
}
