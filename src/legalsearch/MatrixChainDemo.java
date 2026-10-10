
package legalsearch;

public class MatrixChainDemo {

    // p contains matrix dimensions.
    // Matrix Ai has dimensions p[i-1] x p[i].
    public static int minimumMultiplications(int[] p) {
        int n = p.length - 1;

        if (n <= 0) {
            return 0;
        }

        int[][] dp = new int[n + 1][n + 1];

        for (int length = 2; length <= n; length++) {
            for (int i = 1; i <= n - length + 1; i++) {
                int j = i + length - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k]
                            + dp[k + 1][j]
                            + p[i - 1] * p[k] * p[j];

                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {

        // A1 = 10 x 30
        // A2 = 30 x 5
        // A3 = 5 x 60
        int[] dimensions = {10, 30, 5, 60};

        System.out.println(
            "LegalSearch - Matrix Chain Multiplication Demo"
        );

        System.out.println("Matrix dimensions: 10x30, 30x5, 5x60");

        int result = minimumMultiplications(dimensions);

        System.out.println(
            "Minimum scalar multiplications: " + result
        );
    }
}
