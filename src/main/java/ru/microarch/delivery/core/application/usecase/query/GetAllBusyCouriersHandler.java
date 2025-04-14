package ru.microarch.delivery.core.application.usecase.query;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.port.CourierRepository;

@Component
public class GetAllBusyCouriersHandler {

    private final CourierRepository courierRepository;

    public GetAllBusyCouriersHandler(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public List<GetCouriersResponse> handle() {
        List<Courier> busyCouriers = courierRepository.getAllBusyCouriers();
        return busyCouriers.stream().map(this::map).toList();
    }

    private GetCouriersResponse map(Courier courier) {
        return new GetCouriersResponse(courier.getId(), courier.getName(), courier.getLocation());
    }
}
