
package legalsearch;

import java.util.Arrays;
import java.util.Random;

public class ReservoirSamplingDemo {

    public static int[] reservoirSample(
            int[] documentIds, int sampleSize) {

        if (sampleSize < 0 || sampleSize > documentIds.length) {
            throw new IllegalArgumentException(
                    "Sample size must be between 0 and "
                    + documentIds.length);
        }

        int[] reservoir = new int[sampleSize];
        Random random = new Random();

        // Fill the reservoir with the first k items.
        for (int i = 0; i < sampleSize; i++) {
            reservoir[i] = documentIds[i];
        }

        // Consider each remaining item.
        for (int i = sampleSize; i < documentIds.length; i++) {
            int j = random.nextInt(i + 1);

            if (j < sampleSize) {
                reservoir[j] = documentIds[i];
            }
        }

        return reservoir;
    }

    public static void main(String[] args) {

        int[] documentIds = {
            101, 102, 103, 104, 105,
            106, 107, 108, 109, 110
        };

        int sampleSize = 4;

        int[] sample = reservoirSample(documentIds, sampleSize);

        System.out.println(
                "LegalSearch - Reservoir Sampling Demo");

        System.out.println("All document IDs: "
                + Arrays.toString(documentIds));

        System.out.println("Sample size: " + sampleSize);

        System.out.println("Random sample: "
                + Arrays.toString(sample));

        System.out.println("Correct sample size: "
                + (sample.length == sampleSize));
    }
}
