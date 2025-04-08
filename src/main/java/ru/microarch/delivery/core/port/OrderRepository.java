package ru.microarch.delivery.core.port;

import java.util.List;
import java.util.UUID;

import ru.microarch.delivery.core.domain.model.orderaggregate.Order;

public interface OrderRepository {

    void add(Order order);

    void update(Order order);

    Order getById(UUID id);

    Order getAnyCreatedOrder();

    List<Order> getAllAssignedOrders();
}
