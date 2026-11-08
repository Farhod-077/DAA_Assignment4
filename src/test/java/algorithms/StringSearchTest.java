
package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class StringSearchTest {

    NaiveSearch naive = new NaiveSearch();
    KMPSearch kmp = new KMPSearch();

    @Test
    void testLPSReferenceValues() {

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void testSearchReferenceValues() {

        assertEquals(
                List.of(10),
                naive.search("ABABDABACDABABCABAB", "ABABCABAB")
        );

        assertEquals(
                List.of(0, 1, 2, 3),
                kmp.search("AAAAA", "AA")
        );

        assertEquals(
                List.of(0, 2, 4),
                kmp.search("ABABABA", "ABA")
        );
    }

    @Test
    void testNaiveAndKMP() {

        Random random = new Random(42);

        String alphabet = "ABC";

        for (int k = 0; k < 100; k++) {

            String text = randomString(random, alphabet, 20);
            String pattern = randomString(random, alphabet, 5);

            if (pattern.isEmpty()) {
                pattern = "A";
            }

            List<Integer> result1 = naive.search(text, pattern);
            List<Integer> result2 = kmp.search(text, pattern);

            assertEquals(result1, result2);
        }
    }

    @Test
    void testEdgeCases() {

        assertThrows(
                IllegalArgumentException.class,
                () -> naive.search("ABC", "")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> kmp.search("ABC", "")
        );

        assertEquals(
                List.of(),
                naive.search("AB", "ABC")
        );

        assertEquals(
                List.of(),
                kmp.search("AB", "ABC")
        );
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
