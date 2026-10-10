
package legalsearch;

import java.util.Arrays;
import java.util.Random;

public class RandomizedQuickSortDemo {

    private static final Random random = new Random();

    public static void randomizedQuickSort(
            int[] documents, int low, int high) {

        if (low < high) {
            int pivotIndex = partition(documents, low, high);

            randomizedQuickSort(documents, low, pivotIndex - 1);
            randomizedQuickSort(documents, pivotIndex + 1, high);
        }
    }

    private static int partition(
            int[] documents, int low, int high) {

        // Choose a random pivot and move it to the end.
        int randomIndex = low
                + random.nextInt(high - low + 1);

        swap(documents, randomIndex, high);

        int pivot = documents[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (documents[j] <= pivot) {
                i++;
                swap(documents, i, j);
            }
        }

        swap(documents, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {

        int[] documentIds = {
            42, 15, 87, 23, 9, 64, 31, 5
        };

        System.out.println(
                "LegalSearch - Randomized QuickSort Demo");

        System.out.println("Before sorting: "
                + Arrays.toString(documentIds));

        randomizedQuickSort(
                documentIds, 0, documentIds.length - 1);

        System.out.println("After sorting:  "
                + Arrays.toString(documentIds));

        System.out.println("Sorted correctly: "
                + isSorted(documentIds));
    }

    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
