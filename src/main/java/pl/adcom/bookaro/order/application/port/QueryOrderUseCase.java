package pl.adcom.bookaro.order.application.port;

import pl.adcom.bookaro.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
