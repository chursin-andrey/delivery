package ru.microarch.delivery.core.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;

public class DispatchServiceImpl implements DispatchService {

    @Override
    public Courier dispatch(Order order, List<Courier> couriers) {
        Objects.requireNonNull(order);

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Can't dispatch order. It must be in CREATED status to be dispatched, but it is in " + order.getStatus());
        }
        if (couriers.isEmpty()) {
            throw new IllegalArgumentException("Can't dispatch order. There are no free couriers");
        }

        Optional<Courier> fastestCourier = couriers.stream()
                .filter(courier -> courier.getStatus() == CourierStatus.FREE)
                .min(Comparator.comparingInt(courier -> courier.calculateTimeTo(order.getLocation())));

        if (fastestCourier.isEmpty()) {
            throw new IllegalStateException("Can't dispatch order. Suitable courier was not found");
        }

        Courier courier = fastestCourier.get();
        order.assign(courier.getId());
        courier.setBusy();

        return courier;
    }


}
