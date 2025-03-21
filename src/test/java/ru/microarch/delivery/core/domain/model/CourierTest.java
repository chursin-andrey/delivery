package ru.microarch.delivery.core.domain.model;

import java.util.UUID;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;
import ru.microarch.delivery.core.domain.model.courieraggregate.Transport;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourierTest {

    @Nested
    class WhenCreateCourierTest {

        @Test
        void should_CreateCourierWithFreeStatus() {
            String courierName = "Harry";
            String transportName = "Nimbus 2000";
            int transportSpeed = 2;
            Location location = Location.createRandomLocation();

            Courier courier = Courier.create(courierName, transportName, transportSpeed, location);

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(courier.getStatus()).isEqualTo(CourierStatus.FREE);
            soft.assertThat(courier.getId()).isNotNull();
            soft.assertThat(courier.getName()).isEqualTo(courierName);
            soft.assertThat(courier.getTransport().name()).isEqualTo(transportName);
            soft.assertThat(courier.getTransport().speed()).isEqualTo(transportSpeed);
            soft.assertThat(courier.getLocation()).isEqualTo(location);
            soft.assertAll();
        }

        @ParameterizedTest
        @EmptySource
        @NullSource
        void should_NotCreateCourier_when_NameIsEmptyOrNull(String courierName) {
            assertThrows(IllegalArgumentException.class, () -> Courier.create(courierName, "Pedestrian", 1, Location.createRandomLocation()));
        }

        @ParameterizedTest
        @EmptySource
        @NullSource
        void should_NotCreateCourier_when_TransportNameIsEmptyOrNull(String transportName) {
            assertThrows(IllegalArgumentException.class, () -> Courier.create("Harry", transportName, 1, Location.createRandomLocation()));
        }

        @ParameterizedTest
        @NullSource
        void should_NotCreateCourier_when_LocationIsNull(Location location) {
            assertThrows(IllegalArgumentException.class, () -> Courier.create("Harry", "Nimbus 2000", 1, location));
        }
    }

    @Nested
    class WhenMoveToLocationTest {

        @Test
        void should_MoveToAnotherLocation() {
            Location orderLocation = Location.create(5, 5);
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.create(1, 1));

            Location targetLocation = Location.create(2, 1);
            courier.move(targetLocation);

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(courier.getLocation()).isEqualTo(targetLocation);
            soft.assertAll();
        }

        @ParameterizedTest
        @NullSource
        void should_NotMoveToAnotherLocation_when_LocationIsIncorrect(Location targetLocation) {
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.create(1, 1));

            assertThrows(IllegalArgumentException.class, () -> courier.move(targetLocation));
        }

        @ParameterizedTest
        @MethodSource("provideParameters")
        void should_CalculateTimeToLocation(Location courierLocation, Location orderLocation, int speed, int expectedTime) {
            Courier courier = Courier.create("Harry", "Nimbus 2000", speed, courierLocation);

            int actualTime = courier.calculateTimeTo(orderLocation);

            assertEquals(expectedTime, actualTime);
        }

        private static Stream<Arguments> provideParameters(){
            return Stream.of(
                    Arguments.of(Location.create(1, 1), Location.create(5, 5), 2, 4),
                    Arguments.of(Location.create(1, 1), Location.create(5, 10), 1, 13)
            );
        }
    }

    @Nested
    class WhenChangeCourierStatusTest {

        @Test
        void should_ChangeStatusToFree() {
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.createRandomLocation());

            courier.setBusy();
            courier.setFree();

            assertEquals(courier.getStatus(), CourierStatus.FREE);
        }

        @Test
        void should_NotChangeStatusToFree_When_CourierIsAlreadyFree() {
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.createRandomLocation());

            assertThrows(IllegalStateException.class, () -> courier.setFree());
        }

        @Test
        void should_ChangeStatusToBusy() {
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.createRandomLocation());

            courier.setBusy();

            assertEquals(courier.getStatus(), CourierStatus.BUSY);
        }

        @Test
        void should_NotChangeStatusToBusy_When_CourierIsAlreadyBusy() {
            Courier courier = Courier.create("Harry", "Nimbus 2000", 1, Location.createRandomLocation());

            courier.setBusy();

            assertThrows(IllegalStateException.class, () -> courier.setBusy());
        }
    }
}
