package ru.microarch.delivery.core.application.usecase.command.assignorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.service.DispatchService;
import ru.microarch.delivery.core.port.CourierRepository;
import ru.microarch.delivery.core.port.OrderRepository;

import java.util.List;

@Slf4j
@Component
public class AssignOrderHandler {
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final DispatchService dispatchService;

    public AssignOrderHandler(OrderRepository orderRepository,
                              CourierRepository courierRepository,
                              DispatchService dispatchService) {
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
        this.dispatchService = dispatchService;
    }

    @Transactional
    public void handle() {
        List<Courier> freeCouriers = courierRepository.getAllFreeCouriers();
        if (freeCouriers.isEmpty()) {
            log.warn("There are no free couriers to dispatch order");
            return;
        }
        Order order = orderRepository.getAnyCreatedOrder();
        if (order == null) {
            log.warn("There are no orders in CREATED status to dispatch");
            return;
        }
        Courier courier = dispatchService.dispatch(order, freeCouriers);
        orderRepository.update(order);
        courierRepository.update(courier);
        log.info("Order {} was successfully dispatched to courier {}", order, courier);
    }
}
