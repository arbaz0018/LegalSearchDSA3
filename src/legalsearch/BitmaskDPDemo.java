
package legalsearch;

import java.util.Arrays;

public class BitmaskDPDemo {

    // cost[case][lawyer] represents the assignment cost.
    // Each case and each lawyer is assigned exactly once.
    public static int minimumAssignmentCost(int[][] cost) {
        int n = cost.length;

        if (n == 0) {
            return 0;
        }

        for (int[] row : cost) {
            if (row.length != n) {
                throw new IllegalArgumentException(
                    "Cost matrix must be square."
                );
            }
        }

        int totalMasks = 1 << n;
        int[] dp = new int[totalMasks];

        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int mask = 0; mask < totalMasks; mask++) {
            int caseIndex = Integer.bitCount(mask);

            if (caseIndex >= n) {
                continue;
            }

            for (int lawyer = 0; lawyer < n; lawyer++) {
                int bit = 1 << lawyer;

                if ((mask & bit) == 0) {
                    int nextMask = mask | bit;

                    dp[nextMask] = Math.min(
                        dp[nextMask],
                        dp[mask] + cost[caseIndex][lawyer]
                    );
                }
            }
        }

        return dp[totalMasks - 1];
    }

    public static void main(String[] args) {

        // Rows = legal cases; columns = lawyers.
        // Values represent estimated assignment costs.
        int[][] cost = {
            {9, 2, 7},
            {6, 4, 3},
            {5, 8, 1}
        };

        System.out.println("LegalSearch - Bitmask DP Demo");
        System.out.println("Assignment cost matrix:");

        for (int[] row : cost) {
            System.out.println(Arrays.toString(row));
        }

        int answer = minimumAssignmentCost(cost);

        System.out.println(
            "Minimum total assignment cost: " + answer
        );
    }
}
