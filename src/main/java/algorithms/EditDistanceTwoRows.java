
package algorithms;

public class EditDistanceTwoRows {

    private long operations;

    public int distance(String a, String b) {

        operations = 0;

        if (a.length() < b.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        int n = a.length();
        int m = b.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            previous[j] = j;
            operations++;
        }

        for (int i = 1; i <= n; i++) {

            current[0] = i;
            operations++;

            for (int j = 1; j <= m; j++) {

                operations++;

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    current[j] = previous[j - 1];

                } else {

                    int insert = current[j - 1];
                    int delete = previous[j];
                    int replace = previous[j - 1];

                    current[j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }

            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[m];
    }

    public long getOperations() {
        return operations;
    }
}
