package ru.microarch.delivery.core.application.usecase.command.movecourier;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.port.CourierRepository;
import ru.microarch.delivery.core.port.OrderRepository;

@Component
public class MoveCourierHandler {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;

    public MoveCourierHandler(OrderRepository orderRepository, CourierRepository courierRepository) {
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
    }

    @Transactional
    public void handle() {
        List<Order> assignedOrders = orderRepository.getAllAssignedOrders();
        if (assignedOrders.isEmpty()) {
            return;
        }

        for (Order order : assignedOrders) {
            if (order.getCourierId() == null) {
                throw new IllegalStateException("Order in ASSIGNED status with no courierId was found " + order);
            }
            Courier courier = courierRepository.getById(order.getCourierId());
            courier.move(order.getLocation());

            if (courier.getLocation().equals(order.getLocation())) {
                order.complete();
                courier.setFree();
                orderRepository.update(order);
            }
            courierRepository.update(courier);
        }
    }
}
