package pl.adcom.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.adcom.bookaro.catalog.application.CatalogController;
import pl.adcom.bookaro.domain.Book;
import pl.adcom.bookaro.domain.CatalogService;

import java.util.List;

@SpringBootApplication
public class BookaroOnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookaroOnlineShopApplication.class, args);
	}

}
