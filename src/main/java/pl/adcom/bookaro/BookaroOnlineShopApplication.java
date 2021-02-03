package pl.adcom.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookaroOnlineShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookaroOnlineShopApplication.class, args);
	}

	private final CatalogService catalogService;

	public BookaroOnlineShopApplication(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Override
	public void run(String... args) throws Exception {
//		CatalogService service = new CatalogService();
		List<Book> books = catalogService.findByTitle("Pan Tadeusz");
		System.out.println(books);
	}
}
