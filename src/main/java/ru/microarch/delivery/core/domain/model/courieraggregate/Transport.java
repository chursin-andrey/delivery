package ru.microarch.delivery.core.domain.model.courieraggregate;

import java.util.Objects;
import java.util.UUID;
import org.springframework.util.Assert;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

public record Transport(UUID id, String name, int speed) {
    private static final int ALLOWED_MIN_SPEED = 1;
    private static final int ALLOWED_MAX_SPEED= 3;

    public Transport {
        Assert.notNull(id, "id must not be null");
        Assert.hasText(name, "name must not be blank");
        Assert.isTrue(speed >= ALLOWED_MIN_SPEED && speed <= ALLOWED_MAX_SPEED,
                "speed should be in range from %d to %d".formatted(ALLOWED_MIN_SPEED, ALLOWED_MAX_SPEED));
    }

    public static Transport create(String name, int speed) {
        return new Transport(UUID.randomUUID(), name, speed);

    }

    public Location move(Location current, Location target) {
        Objects.requireNonNull(current);
        Objects.requireNonNull(target);

        int xDistance = target.x() - current.x();
        int yDistance = target.y() - current.y();
        int moveX = Math.min(this.speed, Math.abs(xDistance));
        int moveY = Math.min(Math.abs(yDistance), this.speed - moveX);

        return Location.create((int) (current.x() + moveX * Math.signum((float) xDistance)),
                (int) (current.y() + moveY * Math.signum((float) yDistance)));

    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Transport) {
            return this.id.equals(((Transport) other).id);
        }
        return false;
    }
}
