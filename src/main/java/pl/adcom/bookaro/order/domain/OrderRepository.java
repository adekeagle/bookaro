package pl.adcom.bookaro.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findOrderById(Long id);
    void removeById(Long id);
}
