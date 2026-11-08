package algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ExperimentRunner {

    private static final Random random = new Random(42);
    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {

        File folder = new File("results");
        folder.mkdirs();

        File csvFile = new File(folder, "results.csv");

        try (FileWriter writer = new FileWriter(csvFile)) {

            writer.write("algorithm,input,n,run,time_ms,counter\n");

            runEditDistance(writer);
            runStringSearch(writer);
        }

        System.out.println("Experiments completed!");
        System.out.println("Results saved to results/results.csv");
    }

    private static void runEditDistance(FileWriter writer)
            throws IOException {

        int[] sizes = {2, 4, 6, 8, 100, 200, 400, 800};

        for (int n : sizes) {
            for (String type : new String[]{"random", "worst_case"}) {

                String a = generateString(n);
                String b = generateString(n);

                if (type.equals("worst_case")) {
                    a = "A".repeat(n);
                    b = "B".repeat(n);
                }

                for (int run = 1; run <= RUNS; run++) {

                    long start;
                    long end;

                    if (n <= 8) {
                        EditDistanceRecursive recursive =
                                new EditDistanceRecursive();

                        start = System.nanoTime();
                        recursive.distance(a, b);
                        end = System.nanoTime();

                        writeRow(writer, "Recursive", type, n, run,
                                end - start, recursive.getOperations());
                    }

                    EditDistanceTable table = new EditDistanceTable();

                    start = System.nanoTime();
                    table.distance(a, b);
                    end = System.nanoTime();

                    writeRow(writer, "Table", type, n, run,
                            end - start, table.getOperations());

                    EditDistanceTwoRows twoRows =
                            new EditDistanceTwoRows();

                    start = System.nanoTime();
                    twoRows.distance(a, b);
                    end = System.nanoTime();

                    writeRow(writer, "TwoRows", type, n, run,
                            end - start, twoRows.getOperations());
                }
            }
        }
    }

    private static void runStringSearch(FileWriter writer)
            throws IOException {

        int[] sizes = {1000, 2000, 4000, 8000};

        for (int n : sizes) {
            for (String type : new String[]{"random", "worst_case"}) {

                String text;
                String pattern;

                if (type.equals("worst_case")) {
                    text = "A".repeat(n);
                    pattern = "A".repeat(Math.max(1, n / 10 - 1)) + "B";
                } else {
                    text = generateString(n);
                    pattern = generateString(Math.max(1, n / 10));
                }

                for (int run = 1; run <= RUNS; run++) {

                    NaiveSearch naive = new NaiveSearch();

                    long start = System.nanoTime();
                    naive.search(text, pattern);
                    long end = System.nanoTime();

                    writeRow(writer, "Naive", type, n, run,
                            end - start, naive.getComparisons());

                    KMPSearch kmp = new KMPSearch();

                    start = System.nanoTime();
                    kmp.search(text, pattern);
                    end = System.nanoTime();

                    writeRow(writer, "KMP", type, n, run,
                            end - start, kmp.getComparisons());
                }
            }
        }
    }

    private static String generateString(int length) {

        String alphabet = "ABC";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return result.toString();
    }

    private static void writeRow(
            FileWriter writer,
            String algorithm,
            String input,
            int n,
            int run,
            long nanoseconds,
            long counter
    ) throws IOException {

        double milliseconds = nanoseconds / 1_000_000.0;

        writer.write(
                algorithm + "," +
                        input + "," +
                        n + "," +
                        run + "," +
                        milliseconds + "," +
                        counter + "\n"
        );
    }
}