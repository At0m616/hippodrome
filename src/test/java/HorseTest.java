import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HorseTest {

    @Test
    void createHorse_NullableName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0));
    }


    @Test
    void createHorse_NullableName_throwsExpectedMessage() {
        try {
            new Horse(null, 0);
        } catch (Exception e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @EmptySource
    void createHorse_EmptyName_throwsExpectedMessage(String data) {
        try {
            new Horse(data, 0);
        } catch (Exception e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "      ", "\n"})
    void createHorse_EmptyName_throwsException(String data) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(data, 0));
    }

    @Test
    void createHorse_negativeSpeed_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("test", -1));
    }

    @Test
    void createHorse_negativeSpeed_throwsExpectedMessage() {
        try {
            new Horse("test", -1);
        } catch (Exception e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    void createHorse_negativeDistance_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("test", 0, -1));
    }

    @Test
    void createHorse_negativeDistance_throwsExpectedMessage() {
        try {
            new Horse("test", 0, -1);
        } catch (Exception e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void getName() {
        Horse horse = new Horse("horseName", 5, 10);

        assertEquals("horseName", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("name", 5, 10);

        assertEquals(5, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("horseName", 5, 10);

        assertEquals(10, horse.getDistance());
    }

    @ParameterizedTest
    @CsvSource({
            "0.5, 12.5",
            "1, 15.0",
            "3.3, 26.5"
    })
    void move(double methodResult, double expected) {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("horseName", 5, 10);
            when(Horse.getRandomDouble(0.2, 0.9)).thenReturn(methodResult);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
            assertEquals(expected, horse.getDistance());
        }
    }
}