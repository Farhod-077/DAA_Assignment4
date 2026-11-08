
package algorithms;

public class EditDistanceRecursive {

    private long operations;

    /*
     * State:
     * Minimum edits for the first i and j characters.
     *
     * Transition:
     * Match: move diagonally.
     * Otherwise: 1 + min(insert, delete, replace).
     *
     * Base case:
     * If one string is empty, return the other length.
     */

    public int distance(String a, String b) {
        operations = 0;
        return calculate(a, b, a.length(), b.length());
    }

    private int calculate(String a, String b, int i, int j) {

        operations++;

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return calculate(a, b, i - 1, j - 1);
        }

        int insert = calculate(a, b, i, j - 1);
        int delete = calculate(a, b, i - 1, j);
        int replace = calculate(a, b, i - 1, j - 1);

        return 1 + Math.min(insert,
                Math.min(delete, replace));
    }

    public long getOperations() {
        return operations;
    }
}
