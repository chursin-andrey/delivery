package ru.microarch.delivery.core.application.usecase.query;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

@Getter
@AllArgsConstructor
public class GetCouriersResponse {
    private UUID id;
    private String name;
    private Location location;
}
