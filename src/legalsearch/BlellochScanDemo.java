
package legalsearch;

import java.util.Arrays;

public class BlellochScanDemo {

    public static int[] exclusiveScan(int[] input) {

        int n = input.length;

        if (n == 0 || (n & (n - 1)) != 0) {
            throw new IllegalArgumentException(
                    "Array length must be a positive power of two");
        }

        int[] result = Arrays.copyOf(input, n);

        // Upsweep: build partial sums in a tree structure.
        for (int step = 2; step <= n; step *= 2) {
            for (int i = step - 1; i < n; i += step) {
                result[i] += result[i - step / 2];
            }
        }

        // Set the final element to zero for exclusive scan.
        result[n - 1] = 0;

        // Downsweep: distribute prefix sums.
        for (int step = n; step >= 2; step /= 2) {
            for (int i = step - 1; i < n; i += step) {
                int leftIndex = i - step / 2;

                int temp = result[leftIndex];
                result[leftIndex] = result[i];
                result[i] += temp;
            }
        }

        return result;
    }

    public static void main(String[] args) {

        int[] input = {3, 1, 7, 0, 4, 1, 6, 3};

        int[] output = exclusiveScan(input);

        System.out.println(
                "LegalSearch - Blelloch Scan Demo");

        System.out.println("Input: "
                + Arrays.toString(input));

        System.out.println("Exclusive prefix sum: "
                + Arrays.toString(output));

        System.out.println("Expected result: "
                + Arrays.equals(output,
                    new int[]{0, 3, 4, 11, 11, 15, 16, 22}));
    }
}
