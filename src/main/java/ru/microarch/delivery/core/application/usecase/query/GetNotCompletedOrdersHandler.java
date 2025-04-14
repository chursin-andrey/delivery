package ru.microarch.delivery.core.application.usecase.query;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.port.OrderRepository;

@Component
public class GetNotCompletedOrdersHandler {

    private final OrderRepository orderRepository;

    public GetNotCompletedOrdersHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<GetNotCompletedOrdersResponse> handle() {
        List<Order> createdAndAssignedOrders = orderRepository.getAllCreatedAndAssignedOrders();
        return createdAndAssignedOrders.stream().map(this::map).toList();
    }

    private GetNotCompletedOrdersResponse map(Order order) {
        return new GetNotCompletedOrdersResponse(order.getId(), order.getLocation());
    }
}
