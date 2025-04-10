package ru.microarch.delivery.core.domain.model.courieraggregate;

import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Courier {
    private UUID id;
    private String name;
    private Transport transport;
    private Location location;
    private CourierStatus status;

    private Courier(String name, Transport transport, Location location) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.transport = transport;
        this.location = location;
        this.status = CourierStatus.FREE;
    }

    public static Courier create(String name, String transportName, int transportSpeed, Location location) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Courier name is required");
        }
        if (!StringUtils.hasText(transportName)) {
            throw new IllegalArgumentException("Transport name is required");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location must not be null");
        }

        Transport transport = Transport.create(transportName, transportSpeed);
        return new Courier(name, transport, location);
    }

    public void setBusy() {
        if (status == CourierStatus.BUSY) {
            throw new IllegalStateException("Courier %s is already Busy".formatted(this.id));
        }
        this.status = CourierStatus.BUSY;
    }

    public void setFree() {
        if (status == CourierStatus.FREE) {
            throw new IllegalStateException("Courier %s is already Free".formatted(this.id));
        }
        this.status = CourierStatus.FREE;
    }

    public void move(Location target){
        if (target == null) {
            throw new IllegalArgumentException("Target location must not be null");
        }

        Location transportMoveResult = this.transport.move(this.location, target);
        this.location = transportMoveResult;
    }

    public int calculateTimeTo(Location location) {
        double distance = this.location.calculateDistanceTo(location);
        int time = (int) Math.ceil((double) distance / transport.speed());
        return time;
    }
}
