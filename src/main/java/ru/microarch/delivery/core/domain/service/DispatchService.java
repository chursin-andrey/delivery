package ru.microarch.delivery.core.domain.service;

import java.util.List;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;

public interface DispatchService {
    Courier dispatch(Order order, List<Courier> couriers);
}
