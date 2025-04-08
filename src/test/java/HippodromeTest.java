import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class HippodromeTest {


    @Test
    void createHippodrome_NullableConstructor_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void createHippodrome_NullableConstructor_throwsExpectedMessage() {
        try {
            new Hippodrome(null);
        } catch (Exception e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    void createHippodrome_EmptyListConstructor_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    void createHippodrome_EmptyListConstructor_throwsExpectedMessage() {
        try {
            new Hippodrome(Collections.emptyList());
        } catch (Exception e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }


    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("test" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(hippodrome.getHorses(), horses);
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mock = mock(Horse.class, "test" + i);
            horses.add(mock);
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        horses.forEach(horse -> verify(horse, times(1))
                .move());
    }

    @Test
    void getWinner() {
        Horse expectedHorse = new Horse("winner", 1, 6);
        Hippodrome hippodrome = new Hippodrome(List.of(new Horse("slow", 1, 2), expectedHorse));

        Horse actualHorse = hippodrome.getWinner();

        assertEquals(expectedHorse, actualHorse);
    }
}