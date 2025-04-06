package ru.microarch.delivery.core.domain.sharedkernel;

import java.util.Random;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LocationTest {

    @Nested
    class CreateLocationTest {

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {
                "1,1", "2,1", "3,1", "4,1", "5,1", "6,1", "7,1", "8,1", "9,1", "10,1",
                "1,2", "2,2", "3,2", "4,2", "5,2", "6,2", "7,2", "8,2", "9,2", "10,2",
                "1,3", "2,3", "3,3", "4,3", "5,3", "6,3", "7,3", "8,3", "9,3", "10,3",
                "1,4", "2,4", "3,4", "4,4", "5,4", "6,4", "7,4", "8,4", "9,4", "10,4",
                "1,5", "2,5", "3,5", "4,5", "5,5", "6,5", "7,5", "8,5", "9,5", "10,5",
                "1,6", "2,6", "3,6", "4,6", "5,6", "6,6", "7,6", "8,6", "9,6", "10,6",
                "1,7", "2,7", "3,7", "4,7", "5,7", "6,7", "7,7", "8,7", "9,7", "10,7",
                "1,8", "2,8", "3,8", "4,8", "5,8", "6,8", "7,8", "8,8", "9,8", "10,8",
                "1,9", "2,9", "3,9", "4,9", "5,9", "6,9", "7,9", "8,9", "9,9", "10,9",
                "1,10", "2,10", "3,10", "4,10", "5,10", "6,10", "7,10", "8,10", "9,10", "10,10"}
        )
        void shouldSucessfullyCreate_whenCoordinateIsCorrect(int x, int y) {
            var actual = Location.create(x, y);

            assertAll(() -> assertEquals(x, actual.x()), () -> assertEquals(y, actual.y()));
        }

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {
                "0,0",
                "-1,1",
                "11,1",
                "1,-1",
                "1,11"
        })
        void shouldNotCreateLocation_when_CoordinateIsIncorrect(int x, int y) {
            assertThrows(IllegalArgumentException.class, () -> Location.create(x, y));
        }

        @Test
        void shouldNotCreateLocation_when_XCoordinateIsLessThanMinValue() {
            var actual = assertThrows(IllegalArgumentException.class, () -> Location.create(0, 2));

            assertEquals("Expected X value of coordinate should be between '1' and '10'. Actual X value is '0'.", actual.getMessage());
        }

        @Test
        void shouldNotCreateLocation_when_XCoordinateIsGreaterThanMaxValue() {
            var actual = assertThrows(IllegalArgumentException.class, () -> Location.create(11, 1));

            assertEquals("Expected X value of coordinate should be between '1' and '10'. Actual X value is '11'.", actual.getMessage());
        }

        @Test
        void shouldNotCreateLocation_when_YCoordinateIsLessThanMinValue() {
            var actual = assertThrows(IllegalArgumentException.class, () -> Location.create(2, 0));

            assertEquals("Expected Y value of coordinate should be between '1' and '10'. Actual Y value is '0'.", actual.getMessage());
        }

        @Test
        void shouldNotCreateLocation_when_YCoordinateIsGreaterThanMaxValue() {
            var actual = assertThrows(IllegalArgumentException.class, () -> Location.create(1, 12));

            assertEquals("Expected Y value of coordinate should be between '1' and '10'. Actual Y value is '12'.", actual.getMessage());
        }

        @RepeatedTest(value = 100)
        void shouldCreateRandomLocation() {
            Location randomLocation = Location.createRandomLocation();

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(randomLocation).isNotNull();
            soft.assertThat(randomLocation.x()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(10);
            soft.assertThat(randomLocation.y()).isGreaterThanOrEqualTo(1).isLessThanOrEqualTo(10);
            soft.assertAll();
        }

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {
                "1,1", "2,1", "3,1", "4,1", "5,1", "6,1", "7,1", "8,1", "9,1", "10,1",
                "1,2", "2,2", "3,2", "4,2", "5,2", "6,2", "7,2", "8,2", "9,2", "10,2",
                "1,3", "2,3", "3,3", "4,3", "5,3", "6,3", "7,3", "8,3", "9,3", "10,3",
                "1,4", "2,4", "3,4", "4,4", "5,4", "6,4", "7,4", "8,4", "9,4", "10,4",
                "1,5", "2,5", "3,5", "4,5", "5,5", "6,5", "7,5", "8,5", "9,5", "10,5",
                "1,6", "2,6", "3,6", "4,6", "5,6", "6,6", "7,6", "8,6", "9,6", "10,6",
                "1,7", "2,7", "3,7", "4,7", "5,7", "6,7", "7,7", "8,7", "9,7", "10,7",
                "1,8", "2,8", "3,8", "4,8", "5,8", "6,8", "7,8", "8,8", "9,8", "10,8",
                "1,9", "2,9", "3,9", "4,9", "5,9", "6,9", "7,9", "8,9", "9,9", "10,9",
                "1,10", "2,10", "3,10", "4,10", "5,10", "6,10", "7,10", "8,10", "9,10", "10,10"}
        )
        void shouldBeEqual_when_CoordinatesAreEquals(int x, int y) {
            Location location1 = Location.create(x, y);
            Location location2 = Location.create(x, y);

            assertEquals(location1, location2);
        }

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {
                "2,1", "3,1", "4,1", "5,1", "6,1", "7,1", "8,1", "9,1", "10,1",
                "1,2", "3,2", "4,2", "5,2", "6,2", "7,2", "8,2", "9,2", "10,2",
                "1,3", "2,3", "4,3", "5,3", "6,3", "7,3", "8,3", "9,3", "10,3",
                "1,4", "2,4", "3,4", "5,4", "6,4", "7,4", "8,4", "9,4", "10,4",
                "1,5", "2,5", "3,5", "4,5", "6,5", "7,5", "8,5", "9,5", "10,5",
                "1,6", "2,6", "3,6", "4,6", "5,6", "7,6", "8,6", "9,6", "10,6",
                "1,7", "2,7", "3,7", "4,7", "5,7", "6,7", "8,7", "9,7", "10,7",
                "1,8", "2,8", "3,8", "4,8", "5,8", "6,8", "7,8", "9,8", "10,8",
                "1,9", "2,9", "3,9", "4,9", "5,9", "6,9", "7,9", "8,9", "10,9",
                "1,10", "2,10", "3,10", "4,10", "5,10", "6,10", "7,10", "8,10", "9,10"}
        )
        void shouldNotBeEqual_when_CoordinatedAreNotEqual(int x, int y) {
            Location location1 = Location.create(x, y);
            Location location2 = Location.create(y, x);

            assertNotEquals(location1, location2);
        }
    }

    @Nested
    class DistanceToTest {

        @ParameterizedTest
        @CsvSource(delimiter = ',', value = {
                "1, 1, 1, 1, 0",
                "1, 1, 1, 2, 1",
                "1, 2, 1, 1, 1",
                "1, 2, 1, 2, 2",
                "1, 10, 1, 10, 18",
                "4, 2, 9, 6, 5"

        })
        void shouldReturnDistanceBetweenTwoLocations(int currentX, int targetX, int currentY, int targetY, int distance) {
            var currentLocation = Location.create(currentX, currentY);
            var targetLocation = Location.create(targetX, targetY);

            var actual = currentLocation.calculateDistanceTo(targetLocation);

            assertEquals(distance, actual);
        }
    }
}
