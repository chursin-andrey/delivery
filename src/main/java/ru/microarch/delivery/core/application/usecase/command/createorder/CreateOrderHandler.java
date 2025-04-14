package ru.microarch.delivery.core.application.usecase.command.createorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;
import ru.microarch.delivery.core.port.OrderRepository;

@Slf4j
@Component
public class CreateOrderHandler {

    private final OrderRepository orderRepository;

    public CreateOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void handle(CreateOrderCommand createOrderCommand) {
        Order newOrder = Order.create(createOrderCommand.basketId(), Location.createRandomLocation());
        orderRepository.add(newOrder);
        log.info("Order created successfully {}", newOrder);
    }
}
