package ru.microarch.delivery.core.domain.model.sharedkernel;

import java.util.Objects;
import java.util.Random;

public record Location(int x, int y) implements Comparable<Location> {

    private static final int MIN_COORDINATE_VALUE = 1;
    private static final int MAX_COORDINATE_VALUE = 10;

    public static Location create(int x, int y) {
        validateCoordinate(x, y);

        return new Location(x, y);
    }

    private static void validateCoordinate(int x, int y) {
        if (x < MIN_COORDINATE_VALUE || x > MAX_COORDINATE_VALUE) {
            throw new IllegalArgumentException(String.format("Expected X value of coordinate should be between '%d' and '%d'. Actual X value is '%d'.",
                    MIN_COORDINATE_VALUE, MAX_COORDINATE_VALUE, x));
        }
        if (y < MIN_COORDINATE_VALUE || y > MAX_COORDINATE_VALUE) {
            throw new IllegalArgumentException(String.format("Expected Y value of coordinate should be between '%d' and '%d'. Actual Y value is '%d'.",
                    MIN_COORDINATE_VALUE, MAX_COORDINATE_VALUE, y));
        }
    }

    public static Location createRandomLocation() {
        int randomX = new Random().nextInt(MIN_COORDINATE_VALUE, MAX_COORDINATE_VALUE + 1);
        int randomY = new Random().nextInt(MIN_COORDINATE_VALUE, MAX_COORDINATE_VALUE + 1);

        Location location = new Location(randomX, randomY);
        return location;

    }

    public int calculateDistanceTo(Location targetlLocation) {
        Objects.requireNonNull(targetlLocation);

        int diffX = Math.abs(x() - targetlLocation.x());
        int diffY = Math.abs(y() - targetlLocation.y());

        int distance = diffX + diffY;
        return distance;
    }

    @Override
    public int compareTo(Location location) {
        int result = Integer.compare(this.x, location.x());

        if (result == 0) {
            result = Integer.compare(this.y, location.y());
        }
        return result;
    }
}
