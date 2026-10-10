
package legalsearch;

public class DamerauLevenshteinDemo {

    // Optimal String Alignment distance:
    // insertions, deletions, substitutions,
    // and adjacent transpositions.
    public static int distance(String first, String second) {
        int m = first.length();
        int n = second.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = first.charAt(i - 1)
                        == second.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                    Math.min(
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1
                    ),
                    dp[i - 1][j - 1] + cost
                );

                // Adjacent character transposition
                if (i > 1 && j > 1
                        && first.charAt(i - 1)
                            == second.charAt(j - 2)
                        && first.charAt(i - 2)
                            == second.charAt(j - 1)) {

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - 2][j - 2] + 1
                    );
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        String[][] tests = {
            {"teh", "the"},
            {"contrct", "contract"},
            {"fraud", "fraud"},
            {"court", "curt"}
        };

        System.out.println(
            "LegalSearch - Damerau-Levenshtein Demo"
        );

        for (String[] test : tests) {
            System.out.println(
                test[0] + " -> " + test[1]
                + " | Distance: "
                + distance(test[0], test[1])
            );
        }
    }
}
