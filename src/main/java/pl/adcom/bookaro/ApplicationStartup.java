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

    @Override
    public void run(String... args) throws Exception {

        initData();
        searchCatalog();
        placeOrder();

    }

    private void placeOrder() {
        Book wiedzmin = catalog.findOneByTitle("Wiedźmin Tom 1. Ostatnie życzenie").orElseThrow(() -> new IllegalStateException("Cannot find a book 1"));

        Book chlopiZLasu = catalog.findOneByTitle("Chłopiec z lasu").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipient = Recipient
                .builder()
                .name("Zbigniew Nowak")
                .phone("111-111-111")
                .street("Dolna 21")
                .city("Gdańsk")
                .zipCode("11-201")
                .email("zbyszek@example.pl")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(wiedzmin, 16))
                .item(new OrderItem(chlopiZLasu, 7))
                .build();

        PlaceOrderUseCase.PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());


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
        catalog.findOneByTitleAndAuthor("Wiedźmin", "Andrzej Sapkowski")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                                                            .id(book.getId())
                                                            .title("Wiedźmin Tom 1. Ostatnie życzenie")
                                                            .build();

                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

    private void initData() {
        catalog.addBook(new CreateBookCommand("Chłopiec z lasu", "Harlan Coben", 2020, new BigDecimal("27.29")));
        catalog.addBook(new CreateBookCommand("Wiedźmin", "Andrzej Sapkowski", 1998, new BigDecimal("32.40")));
        catalog.addBook(new CreateBookCommand("Folwark Zwierzęcy", "George Orwell", 1985, new BigDecimal("10.00")));
        catalog.addBook(new CreateBookCommand("Obiecaj, że wrócisz", "Beata Majewska", 2020, new BigDecimal("25.90")));
    }

    private void extracted() {
        List<Book> books = catalog.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);

        List<Book> booksFindedByauthor = catalog.findByAuthor("Andrzej");
        booksFindedByauthor.stream().forEach(System.out::println);
    }
}
