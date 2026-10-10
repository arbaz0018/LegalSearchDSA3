
package legalsearch;

import java.util.Arrays;

public class SuffixArrayIndex {

    // 1. Build Suffix Array by sorting all suffix starting positions
    public static Integer[] buildSuffixArray(String text) {
        int n = text.length();
        Integer[] suffixes = new Integer[n];

        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        Arrays.sort(suffixes,
            (a, b) -> text.substring(a).compareTo(text.substring(b)));

        return suffixes;
    }

    // 2. Build LCP Array using the basic comparison method
    public static int[] buildLCP(String text, Integer[] suffixArray) {
        int n = text.length();
        int[] lcp = new int[n];

        for (int i = 1; i < n; i++) {
            int a = suffixArray[i - 1];
            int b = suffixArray[i];
            int length = 0;

            while (a + length < n && b + length < n
                    && text.charAt(a + length)
                    == text.charAt(b + length)) {
                length++;
            }

            lcp[i] = length;
        }

        return lcp;
    }

    // 3. Build LCP Array using Kasai's Algorithm - O(n)
    public static int[] buildLCPKasai(
            String text, Integer[] suffixArray) {

        int n = text.length();
        int[] lcp = new int[n];
        int[] rank = new int[n];

        // Store the sorted rank of each suffix
        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int common = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            // The first suffix has no previous suffix
            if (r == 0) {
                common = 0;
                continue;
            }

            int previous = suffixArray[r - 1];

            // Count common prefix characters
            while (i + common < n
                    && previous + common < n
                    && text.charAt(i + common)
                    == text.charAt(previous + common)) {
                common++;
            }

            lcp[r] = common;

            // Reuse previous comparison work
            if (common > 0) {
                common--;
            }
        }

        return lcp;
    }

    // 4. Main method to test both implementations
    public static void main(String[] args) {
        String text = "banana";

        Integer[] suffixArray = buildSuffixArray(text);

        int[] basicLCP = buildLCP(text, suffixArray);
        int[] kasaiLCP = buildLCPKasai(text, suffixArray);

        System.out.println("LegalSearch - Suffix Array Demo");
        System.out.println("Text: " + text);
        System.out.println("\nSorted suffixes:");

        for (int i = 0; i < suffixArray.length; i++) {
            int start = suffixArray[i];

            System.out.printf(
                "Position %d: %s | LCP: %d%n",
                start,
                text.substring(start),
                kasaiLCP[i]
            );
        }

        System.out.println("\nBasic LCP and Kasai LCP match: "
                + Arrays.equals(basicLCP, kasaiLCP));
    }
}
