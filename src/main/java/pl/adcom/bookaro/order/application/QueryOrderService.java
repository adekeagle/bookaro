package pl.adcom.bookaro.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adcom.bookaro.order.application.port.QueryOrderUseCase;
import pl.adcom.bookaro.order.domain.Order;
import pl.adcom.bookaro.order.domain.OrderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return Optional.of(repository
                .findAll()
                .stream()
                .filter(order -> order.getId() == id).findFirst().get());
    }

    @Override
    public Order addOrder(CreateOrderCommand command) {
        Order order = command.toOrder();
        return repository.save(order);
    }

    @Override
    public void removeById(Long id) {
        repository.removeById(id);
    }
}
