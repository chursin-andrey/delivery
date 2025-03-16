package ru.microarch.delivery.core.domain.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.microarch.delivery.core.domain.model.courieraggregate.Transport;

import java.util.UUID;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TransportTest {

    @Nested
    class CreateTransportTest {

        @ParameterizedTest
        @CsvSource(value = {
                "Pedestrian, 1",
                "Bicycle, 2",
                "Car, 3"
        })
        void should_AllowCreation_WhenParamsAreCorrect(String name, int speed) {
            Transport transport = Transport.create(name, speed);

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(transport).isNotNull();
            soft.assertThat(transport.id()).isNotNull();
            soft.assertThat(transport.name()).isEqualTo(name);
            soft.assertThat(transport.speed()).isEqualTo(speed);
            soft.assertAll();
        }

        @Test
        void should_NotAllowCreation_WhenInvalidParams() {
            assertThrows(IllegalArgumentException.class, () -> Transport.create(null, 1));
            assertThrows(IllegalArgumentException.class, () -> Transport.create("", 1));
            assertThrows(IllegalArgumentException.class, () -> Transport.create(" ", 1));
        }

        @Test
        void should_NotAllowCreation_WhenSpeedIsOutOfRange(){
            assertThrows(IllegalArgumentException.class, () -> Transport.create("Scooter", 0));
            assertThrows(IllegalArgumentException.class, () -> Transport.create("Scooter", 4));
            assertThrows(IllegalArgumentException.class, () -> Transport.create("Scooter", -1));
        }

        @Test
        void should_ReturnTrue_WhenTwoTransportsAreEqual(){
            UUID uuid = UUID.randomUUID();
            Transport transportFirst = new Transport(uuid, "Bicycle", 1);
            Transport transportSecond = new Transport(uuid, "Car", 2);
            assertTrue(transportFirst.equals(transportSecond));
        }

        @Test
        void should_ReturnFalse_WhenTwoTransportsAreNotEqual() {
            Transport transportFirst = Transport.create("Scooter",1);
            Transport transportSecond = Transport.create("Scooter", 1);
            assertFalse(transportFirst.equals(transportSecond));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Pedestrian, 1, 1, 1, 1, 1 ,1, 1", // Пешеход, заказ X:совпадает, Y: совпадает
            "Pedestrian, 1, 5, 5, 5, 5, 5, 5", // Пешеход, заказ X:совпадает, Y: совпадает
            "Pedestrian, 1, 1, 1, 1, 2, 1, 2", // Пешеход, заказ X:совпадает, Y: выше
            "Pedestrian, 1, 1, 1, 1, 5, 1, 2", // Пешеход, заказ X:совпадает, Y: выше
            "Pedestrian, 1, 2, 2, 3, 2, 3, 2", // Пешеход, заказ X:правее, Y: совпадает
            "Pedestrian, 1, 5, 5, 6, 5, 6, 5", // Пешеход, заказ X:правее, Y: совпадает
            "Pedestrian, 1, 2, 2, 3, 3, 3, 2", // Пешеход, заказ X:правее, Y: выше
            "Pedestrian, 1, 1, 1, 5, 5, 2, 1", // Пешеход, заказ X:правее, Y: выше
            "Pedestrian, 1, 1, 2, 1, 1, 1, 1", // Пешеход, заказ X:совпадает, Y: ниже
            "Pedestrian, 1, 5, 5, 5, 1, 5, 4", // Пешеход, заказ X:совпадает, Y: ниже
            "Pedestrian, 1, 2, 2, 1, 2, 1, 2", // Пешеход, заказ X:левее, Y: совпадает
            "Pedestrian, 1, 5, 5, 1, 5, 4, 5", // Пешеход, заказ X:левее, Y: совпадает
            "Pedestrian, 1, 2, 2, 1, 1, 1, 2", // Пешеход, заказ X:левее, Y: ниже
            "Pedestrian, 1, 5, 5, 1, 1, 4, 5", // Пешеход, заказ X:левее, Y: ниже
            "Bicycle, 2, 1, 1, 1, 1, 1, 1", // Велосипедист, заказ X:совпадает, Y: совпадает
            "Bicycle, 2, 5, 5, 5, 5, 5, 5", // Велосипедист, заказ X:совпадает, Y: совпадает
            "Bicycle, 2, 1, 1, 1, 3, 1, 3", // Велосипедист, заказ X:совпадает, Y: выше
            "Bicycle, 2, 1, 1, 1, 5, 1, 3", // Велосипедист, заказ X:совпадает, Y: выше
            "Bicycle, 2, 2, 2, 4, 2, 4, 2", // Велосипедист, заказ X:правее, Y: совпадает
            "Bicycle, 2, 5, 5, 8, 5, 7, 5", // Велосипедист, заказ X:правее, Y: совпадает
            "Bicycle, 2, 2, 2, 4, 4, 4, 2", // Велосипедист, заказ X:правее, Y: выше
            "Bicycle, 2, 1, 1, 5, 5, 3, 1", // Велосипедист, заказ X:правее, Y: выше
            "Bicycle, 2, 1, 3, 1, 1, 1, 1", // Велосипедист, заказ X:совпадает, Y: ниже
            "Bicycle, 2, 5, 5, 5, 1, 5, 3", // Велосипедист, заказ X:совпадает, Y: ниже
            "Bicycle, 2, 3, 2, 1, 2, 1, 2", // Велосипедист, заказ X:левее, Y: совпадает
            "Bicycle, 2, 5, 5, 1, 5, 3, 5", // Велосипедист, заказ X:левее, Y: совпадает
            "Bicycle, 2, 3, 3, 1, 1, 1, 3", // Велосипедист, заказ X:левее, Y: ниже
            "Bicycle, 2, 5, 5, 1, 1, 3, 5", // Велосипедист, заказ X:левее, Y: ниже
            "Bicycle, 2, 1, 1, 1, 2, 1, 2", // Велосипедист, заказ ближе чем скорость
            "Bicycle, 2, 1, 1, 2, 1, 2, 1", // Велосипедист, заказ ближе чем скорость
            "Bicycle, 2, 5, 5, 5, 4, 5, 4", // Велосипедист, заказ ближе чем скорость
            "Bicycle, 2, 5, 5, 4, 5, 4, 5", // Велосипедист, заказ ближе чем скорость
            "Bicycle, 2, 1, 1, 2, 2, 2, 2", // Велосипедист, заказ с шагами по 2 осям
            "Bicycle, 2, 5, 5, 4, 4, 4, 4", // Велосипедист, заказ с шагами по 2 осям
            "Bicycle, 2, 1, 1, 1, 2, 1, 2", // Велосипедист, заказ с шагами по 2 осям
            "Bicycle, 2, 5, 5, 5, 4, 5, 4", // Велосипедист, заказ с шагами по 2 осям
            "Car, 3, 1, 1, 1, 1, 1, 1", // Автомобилист, заказ X:совпадает, Y: совпадает
            "Car, 3, 5, 5, 5, 5, 5, 5", // Автомобилист, заказ X:совпадает, Y: совпадает
            "Car, 3, 1, 1, 1, 4, 1, 4", // Автомобилист, заказ X:совпадает, Y: выше
            "Car, 3, 1, 1, 1, 9, 1, 4", // Автомобилист, заказ X:совпадает, Y: выше
            "Car, 3, 2, 2, 5, 2, 5, 2", // Автомобилист, заказ X:правее, Y: совпадает
            "Car, 3, 5, 5, 9, 5, 8, 5", // Автомобилист, заказ X:правее, Y: совпадает
            "Car, 3, 2, 2, 4, 4, 4, 3",// Автомобилист, заказ X:правее, Y: выше
            "Car, 3, 1, 1, 5, 5, 4, 1",// Автомобилист, заказ X:правее, Y: выше
            "Car, 3, 1, 4, 1, 1, 1, 1",// Автомобилист, заказ X:совпадает, Y: ниже
            "Car, 3, 5, 5, 5, 1, 5, 2", // Автомобилист, заказ X:совпадает, Y: ниже
            "Car, 3, 4, 2, 1, 2, 1, 2", // Автомобилист, заказ X:левее, Y: совпадает
            "Car, 3, 5, 5, 1, 5, 2, 5", // Автомобилист, заказ X:левее, Y: совпадает
            "Car, 3, 4, 3, 1, 1, 1, 3", // Автомобилист, заказ X:левее, Y: ниже
            "Car, 3, 4, 6, 1, 5, 1, 6", //Автомобилист, заказ X:левее, Y: ниже
            "Car, 3, 4, 5, 4, 10, 4, 8", // Автомобилист, заказ X:совпадает, Y: ниже
            "Car, 3, 1, 9, 1, 5, 1, 6", // Автомобилист, заказ X:совпадает, Y: выше
            "Car, 3, 3, 3, 3, 4, 3, 4", // Move down
            "Car, 3, 8, 4, 7, 2, 7, 2", // Move left up
            "Car, 3, 4, 6, 2, 4, 2, 5", // Move left up
            "Car, 3, 3, 3, 6, 3, 6, 3", // Move right
            "Car, 3, 6, 3, 3, 3, 3, 3", // Move left
            "Car, 3, 2, 5, 2, 7, 2, 7", // Move down
            "Car, 3, 2, 5, 2, 3, 2, 3", // Move up
            "Car, 3, 2, 6, 3, 4, 3, 4", // Move up right
            "Car, 3, 4, 6, 3, 7, 3, 7", // Move left down

    })
    void should_ReturnCorrectTargetLocation(String name, int speed, int fromX, int fromY, int toX, int toY, int expectedX, int expectedY) {
        Transport transport = Transport.create(name, speed);
        Location from = Location.create(fromX, fromY);
        Location to = Location.create(toX, toY);
        Location actual = transport.move(from, to);

        Location expected = Location.create(expectedX, expectedY);

        assertTrue(expected.equals(actual));
    }

}
