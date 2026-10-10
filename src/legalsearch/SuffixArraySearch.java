
package legalsearch;

import java.util.Arrays;

public class SuffixArraySearch {

    public static Integer[] buildSuffixArray(String text) {
        Integer[] suffixes = new Integer[text.length()];

        for (int i = 0; i < text.length(); i++) {
            suffixes[i] = i;
        }

        Arrays.sort(suffixes,
            (a, b) -> text.substring(a).compareTo(text.substring(b)));

        return suffixes;
    }

    public static boolean search(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }

        Integer[] suffixArray = buildSuffixArray(text);
        int left = 0;
        int right = suffixArray.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int start = suffixArray[mid];
            String suffix = text.substring(start);

            int comparison = suffix.compareTo(pattern);

            if (suffix.startsWith(pattern)) {
                return true;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
    

public static int[] buildLCPKasai(String text, Integer[] suffixArray) {
    int n = text.length();
    int[] lcp = new int[n];
    int[] rank = new int[n];

    for (int i = 0; i < n; i++) {
        rank[suffixArray[i]] = i;
    }

    int common = 0;

    for (int i = 0; i < n; i++) {
        int r = rank[i];

        if (r == 0) {
            common = 0;
            continue;
        }

        int previous = suffixArray[r - 1];

        while (i + common < n
                && previous + common < n
                && text.charAt(i + common)
                    == text.charAt(previous + common)) {
            common++;
        }

        lcp[r] = common;

        if (common > 0) {
            common--;
        }
    }

    return lcp;
}


    public static void main(String[] args) {
        String document =
            "Indian law covers contracts, fraud and legal disputes.";

        String[] patterns = {
            "contracts", "fraud", "criminal", "legal"
        };

        System.out.println("LegalSearch - Suffix Array Search");

        for (String pattern : patterns) {
            System.out.println(
                pattern + " -> " +
                (search(document, pattern) ? "FOUND" : "NOT FOUND")
            );
        }
    }
}
