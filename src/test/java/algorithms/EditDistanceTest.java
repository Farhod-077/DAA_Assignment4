
package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EditDistanceTest {

    EditDistanceRecursive recursive = new EditDistanceRecursive();
    EditDistanceTable table = new EditDistanceTable();
    EditDistanceTwoRows twoRows = new EditDistanceTwoRows();

    @Test
    void testReferenceValues() {

        assertEquals(3, recursive.distance("kitten", "sitting"));
        assertEquals(2, recursive.distance("flaw", "lawn"));
        assertEquals(5, recursive.distance("intention", "execution"));

        assertEquals(3, table.distance("", "abc"));
        assertEquals(0, table.distance("abc", "abc"));

        assertEquals(3, twoRows.distance("", "abc"));
        assertEquals(0, twoRows.distance("abc", "abc"));
    }

    @Test
    void testAllThreeVersions() {

        Random random = new Random(42);

        String alphabet = "abc";

        for (int k = 0; k < 100; k++) {

            String a = randomString(random, alphabet, 7);
            String b = randomString(random, alphabet, 7);

            int result1 = recursive.distance(a, b);
            int result2 = table.distance(a, b);
            int result3 = twoRows.distance(a, b);

            assertEquals(result1, result2);
            assertEquals(result1, result3);
        }
    }

    private String randomString(
            Random random, String alphabet, int maxLength) {

        int length = random.nextInt(maxLength + 1);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(
                    alphabet.charAt(random.nextInt(alphabet.length()))
            );
        }

        return result.toString();
    }
}
