package pl.adcom.bookaro.order.domain;

import lombok.Value;
import pl.adcom.bookaro.catalog.domain.Book;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
