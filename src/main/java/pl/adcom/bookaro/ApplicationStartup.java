package pl.adcom.bookaro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.adcom.bookaro.catalog.application.port.CatalogUseCase;
import pl.adcom.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.adcom.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.adcom.bookaro.catalog.domain.Book;
import pl.adcom.bookaro.order.application.port.PlaceOrderUseCase;
import pl.adcom.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.adcom.bookaro.order.application.port.QueryOrderUseCase;
import pl.adcom.bookaro.order.domain.OrderItem;
import pl.adcom.bookaro.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

@Component
//@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final String author;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalog,
                              PlaceOrderUseCase placeOrder,
                              QueryOrderUseCase queryOrder,
                              @Value("${bookaro.catalog.query}") String title,
                              @Value("${bookaro.catalog.limit:3}") Long limit,
                              @Value("${bookaro.catalog.author}") String author) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    //    public ApplicationStartup(CatalogController catalogController) {
//        this.catalogController = catalogController;
//    }

    @Override
    public void run(String... args) throws Exception {

        initData();
        searchCatalog();
        placeOrder();
//        extracted();


    }

    private void placeOrder() {
        //find pan tadeusz
        Book panTadeusz = catalog.findOneByTitle("Pan").orElseThrow(() -> new IllegalStateException("Cannot find a book 1"));

        //find chłopi
        Book chopi = catalog.findOneByTitle("Chlopi").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        //create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("111-111-111")
                .street("Dolna 21")
                .city("Gdańsk")
                .zipCode("11-201")
                .email("jan.kowalski@example.org")
                .build();

        //place order command
        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chopi, 7))
                .build();

        PlaceOrderUseCase.PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());
        //list all orders
        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS " + order);
                });
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }


    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book ....");
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                                                            .id(book.getId())
                                                            .title("Pan")
                                                            .build();


//                    UpdateBookCommand command = new UpdateBookCommand(
//                            book.getId(),
//                            "Harry Potter",
//                            book.getAuthor(),
//                            book.getYear()
//                    );
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1998, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Ogniem i Mieczem", "Henryk Sienkiewicz", 1998, new BigDecimal("29.90")));
        catalog.addBook(new CreateBookCommand("Chlopi", "Władysław Reymont", 1954, new BigDecimal("11.90")));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 2005, new BigDecimal("14.90")));
    }

    private void extracted() {
        //		CatalogService service = new CatalogService();
        List<Book> books = catalog.findByTitle(title);
//        books.forEach(System.out::println);
        books.stream().limit(limit).forEach(System.out::println);
        List<Book> booksFindedByauthor = catalog.findByAuthor("Andrzej");
        booksFindedByauthor.stream().forEach(System.out::println);
    }
}
