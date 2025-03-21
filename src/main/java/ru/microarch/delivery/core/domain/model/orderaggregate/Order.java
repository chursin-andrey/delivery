package ru.microarch.delivery.core.domain.model.orderaggregate;

import java.util.UUID;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

public class Order {
    private final UUID id;
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

    public UUID getCourierId(){
        return courierId;
    }

    public Location getLocation() {
        return location;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id != null ? id.equals(order.id) : order.id == null;
    }
}
