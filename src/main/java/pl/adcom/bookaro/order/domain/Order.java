package pl.adcom.bookaro.order.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Order {
    private Long id;
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderItem> items;
    private Recipient recipient;
    private LocalDateTime createdAt;

    public Order(OrderStatus status, List<OrderItem> items, Recipient recipient, LocalDateTime createdAt) {
        this.status = this.status;
        this.items = items;
        this.recipient = recipient;
        this.createdAt = createdAt;
    }

    public BigDecimal totalPrice(){
        return items.stream()
                .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
