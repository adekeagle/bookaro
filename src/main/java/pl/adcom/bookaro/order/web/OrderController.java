package pl.adcom.bookaro.order.web;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.adcom.bookaro.order.application.port.QueryOrderUseCase;
import pl.adcom.bookaro.order.domain.Order;
import pl.adcom.bookaro.order.domain.OrderItem;
import pl.adcom.bookaro.order.domain.OrderStatus;
import pl.adcom.bookaro.order.domain.Recipient;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/orders")
@RestController
@AllArgsConstructor
public class OrderController {

    private final QueryOrderUseCase queryOrderUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return queryOrderUseCase.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Order> getOrderById(@PathVariable Long id){
        return queryOrderUseCase.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addOrder(@Valid @RequestBody OrderController.RestOrderCommand command){
        Order order = queryOrderUseCase.addOrder(command.toCreateCommand());
        return ResponseEntity.created(createdOrderUri(order)).build();

    }

    private URI createdOrderUri(Order order) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + order.getId().toString()).build().toUri();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        queryOrderUseCase.removeById(id);
    }

    @Value
    private static class RestOrderCommand {
        private OrderStatus status;
        private List<OrderItem> items;
        private Recipient recipient;
        private LocalDateTime createdAt;

        QueryOrderUseCase.CreateOrderCommand toCreateCommand(){
            return new QueryOrderUseCase.CreateOrderCommand(status, items, recipient, createdAt);
        }
    }
}
