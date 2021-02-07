package pl.adcom.bookaro.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adcom.bookaro.order.application.port.QueryOrderUseCase;
import pl.adcom.bookaro.order.domain.Order;
import pl.adcom.bookaro.order.domain.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }
}
