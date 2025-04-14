package ru.microarch.delivery.core.application.usecase.command.createorder;

import java.util.UUID;
import org.springframework.util.Assert;

public record CreateOrderCommand(
        UUID basketId,
        String street
) {
    public CreateOrderCommand {
        Assert.notNull(basketId, "basketId must not be null");
        Assert.hasText(street, "street must not be blank");
    }
}
