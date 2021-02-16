package pl.adcom.bookaro.order.application.port;

import lombok.Builder;
import lombok.Value;
import pl.adcom.bookaro.catalog.domain.Book;
import pl.adcom.bookaro.order.domain.Order;
import pl.adcom.bookaro.order.domain.OrderItem;
import pl.adcom.bookaro.order.domain.OrderStatus;
import pl.adcom.bookaro.order.domain.Recipient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<Order> findAll();

    Optional<Order> findOrderById(Long id);

    Order addOrder(CreateOrderCommand command);

    void removeById(Long id);

    @Value
    class CreateOrderCommand{
        OrderStatus status;
        List<OrderItem> items;
        Recipient recipient;
        LocalDateTime createdA;

        public Order toOrder() {
            return new Order(status, items, recipient, createdA);
        }
    }

    @Value
    class CreateOrderItemCommand{
        Book book;
        int quantity;

        public CreateOrderItemCommand(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }
    }

    @Value
    class CreateRecipient{
        String name;
        String phone;
        String street;
        String city;
        String zipCode;
        String email;

        public CreateRecipient(String name, String phone, String street, String city, String zipCode, String email) {
            this.name = name;
            this.phone = phone;
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
            this.email = email;
        }
    }

//    @Value
//    class UpdateOrderStatus{
//        private Long id;
//        private OrderStatus status;
//
//        public UpdateOrderStatus(Long id, OrderStatus status) {
//            this.id = id;
//            this.status = status;
//        }
//    }
}
