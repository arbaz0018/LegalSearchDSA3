
package legalsearch;

import java.util.Arrays;

public class ParallelReduceDemo {

    public static int parallelReduceSum(int[] input) {

        if (input.length == 0) {
            return 0;
        }

        int[] current = Arrays.copyOf(input, input.length);

        // Each stage combines adjacent pairs.
        while (current.length > 1) {

            int nextSize = (current.length + 1) / 2;
            int[] next = new int[nextSize];

            for (int i = 0; i < current.length / 2; i++) {
                next[i] = current[2 * i] + current[2 * i + 1];
            }

            // Carry an unpaired element into the next stage.
            if (current.length % 2 != 0) {
                next[nextSize - 1] = current[current.length - 1];
            }

            current = next;
        }

        return current[0];
    }

    public static void main(String[] args) {

        int[] documentScores = {10, 20, 15, 5, 30, 10, 25, 5};

        System.out.println(
                "LegalSearch - Parallel Reduce Demo");

        System.out.println("Document scores: "
                + Arrays.toString(documentScores));

        int total = parallelReduceSum(documentScores);

        System.out.println("Total score: " + total);

        System.out.println("Expected result: "
                + (total == 120));
    }
}
