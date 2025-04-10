package ru.microarch.delivery.core.port;

import java.util.List;
import java.util.UUID;

import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;

public interface CourierRepository {

    void add(Courier courier);

    void update(Courier courier);

    Courier getById(UUID id);

    List<Courier> getAllFreeCouriers();
}
