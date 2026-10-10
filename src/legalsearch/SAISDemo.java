
package legalsearch;

import java.util.Arrays;

public class SAISDemo {

    // Build suffix array using SA-IS
    public static int[] buildSuffixArray(String text) {
        int n = text.length();

        if (n == 0) {
            return new int[0];
        }

        // Convert characters to positive integers.
        // Zero is reserved for the unique sentinel.
        int[] s = new int[n + 1];

        for (int i = 0; i < n; i++) {
            s[i] = text.charAt(i) + 1;
        }

        s[n] = 0;

        int[] full = sais(s, 65536);

        // Remove the sentinel suffix at position n.
        int[] result = new int[n];
        int j = 0;

        for (int position : full) {
            if (position != n) {
                result[j++] = position;
            }
        }

        return result;
    }

    // SA-IS recursive suffix array construction
    private static int[] sais(int[] s, int upper) {
        int n = s.length;
        int[] sa = new int[n];

        if (n == 1) {
            sa[0] = 0;
            return sa;
        }

        if (n == 2) {
            if (s[0] < s[1]) {
                sa[0] = 0;
                sa[1] = 1;
            } else {
                sa[0] = 1;
                sa[1] = 0;
            }
            return sa;
        }

        boolean[] isS = new boolean[n];
        isS[n - 1] = true;

        for (int i = n - 2; i >= 0; i--) {
            isS[i] = s[i] == s[i + 1]
                    ? isS[i + 1]
                    : s[i] < s[i + 1];
        }

        int[] sumL = new int[upper + 2];
        int[] sumS = new int[upper + 2];

        for (int i = 0; i < n; i++) {
            if (!isS[i]) {
                sumS[s[i]]++;
            } else {
                sumL[s[i] + 1]++;
            }
        }

        for (int i = 0; i <= upper; i++) {
            sumS[i] += sumL[i];
            sumL[i + 1] += sumS[i];
        }

        int[] lmsMap = new int[n + 1];
        Arrays.fill(lmsMap, -1);

        int m = 0;

        for (int i = 1; i < n; i++) {
            if (!isS[i - 1] && isS[i]) {
                lmsMap[i] = m++;
            }
        }

        int[] lms = new int[m];
        int p = 0;

        for (int i = 1; i < n; i++) {
            if (!isS[i - 1] && isS[i]) {
                lms[p++] = i;
            }
        }

        induce(s, sa, isS, sumL, sumS, lms);

        if (m > 0) {
            int[] sortedLms = new int[m];
            p = 0;

            for (int value : sa) {
                if (value >= 0 && lmsMap[value] != -1) {
                    sortedLms[p++] = value;
                }
            }

            int[] reduced = new int[m];
            int reducedUpper = 0;

            reduced[lmsMap[sortedLms[0]]] = 0;

            for (int i = 1; i < m; i++) {
                int left = sortedLms[i - 1];
                int right = sortedLms[i];

                int leftEnd =
                        lmsMap[left] + 1 < m
                        ? lms[lmsMap[left] + 1] : n;

                int rightEnd =
                        lmsMap[right] + 1 < m
                        ? lms[lmsMap[right] + 1] : n;

                boolean same =
                        leftEnd - left == rightEnd - right;

                if (same) {
                    while (left < leftEnd) {
                        if (s[left] != s[right]
                                || isS[left] != isS[right]) {
                            same = false;
                            break;
                        }

                        left++;
                        right++;
                    }
                }

                if (!same) {
                    reducedUpper++;
                }

                reduced[lmsMap[sortedLms[i]]] = reducedUpper;
            }

            int[] reducedSa = sais(reduced, reducedUpper);

            for (int i = 0; i < m; i++) {
                sortedLms[i] = lms[reducedSa[i]];
            }

            induce(s, sa, isS, sumL, sumS, sortedLms);
        }

        return sa;
    }

    // Induce the ordering of L-type and S-type suffixes
    private static void induce(
            int[] s,
            int[] sa,
            boolean[] isS,
            int[] sumL,
            int[] sumS,
            int[] lms) {

        int n = s.length;
        Arrays.fill(sa, -1);

        int[] buffer = sumS.clone();

        for (int d : lms) {
            if (d < n) {
                sa[buffer[s[d]]++] = d;
            }
        }

        buffer = sumL.clone();
        sa[buffer[s[n - 1]]++] = n - 1;

        for (int i = 0; i < n; i++) {
            int v = sa[i];

            if (v >= 1 && !isS[v - 1]) {
                sa[buffer[s[v - 1]]++] = v - 1;
            }
        }

        buffer = sumL.clone();

        for (int i = n - 1; i >= 0; i--) {
            int v = sa[i];

            if (v >= 1 && isS[v - 1]) {
                sa[--buffer[s[v - 1] + 1]] = v - 1;
            }
        }
    }

    // Run tests
    public static void main(String[] args) {
        String[] tests = {
            "banana",
            "mississippi",
            "aaaaa",
            "abcde",
            "abababa"
        };

        System.out.println("LegalSearch - SA-IS Verification");

        for (String text : tests) {
            int[] suffixArray = buildSuffixArray(text);

            System.out.println("\nText: " + text);
            System.out.println("Suffix Array: "
                    + Arrays.toString(suffixArray));
        }
    }
}
