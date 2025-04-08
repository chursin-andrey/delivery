package ru.microarch.delivery.core.domain.model.orderaggregate;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    private UUID id;
    private Location location;
    private OrderStatus status;
    private UUID courierId;

    private Order(UUID id, Location location){
        this.id = id;
        this.location = location;
        this.status = OrderStatus.CREATED;
    }

    public static Order create(UUID id, Location location) {
        Order order = new Order(id, location);
        return order;
    }

    public void assign(UUID courierId) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Can't assign order with id=%s and status=%s for courier (courierId=%s)."
                .formatted(this.id, this,status, courierId));
        }
        this.courierId = courierId;
        this.status = OrderStatus.ASSIGNED;
    }

    public void complete() {
        if (status != OrderStatus.ASSIGNED) {
            throw new IllegalStateException("Cannot complete order with id=%s and status=%s".formatted(this.id, this.status));
        }
        this.status = OrderStatus.COMPLETED;
    }
}
