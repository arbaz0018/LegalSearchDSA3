
package legalsearch;

public class WeightedEditDistanceDemo {

    // Weighted edit distance:
    // insertionCost, deletionCost and substitutionCost
    // can be configured independently.
    public static int distance(
            String source,
            String target,
            int insertionCost,
            int deletionCost,
            int substitutionCost) {

        int m = source.length();
        int n = target.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i * deletionCost;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j * insertionCost;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                int cost = source.charAt(i - 1)
                        == target.charAt(j - 1)
                        ? 0 : substitutionCost;

                int delete = dp[i - 1][j] + deletionCost;
                int insert = dp[i][j - 1] + insertionCost;
                int substitute = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(
                    Math.min(delete, insert),
                    substitute
                );
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {

        String source = "contrct";
        String target = "contract";

        int result = distance(
            source,
            target,
            1,  // Insertion cost
            1,  // Deletion cost
            2   // Substitution cost
        );

        System.out.println("LegalSearch - Weighted Edit Distance");
        System.out.println("Source: " + source);
        System.out.println("Target: " + target);
        System.out.println("Insertion cost: 1");
        System.out.println("Deletion cost: 1");
        System.out.println("Substitution cost: 2");
        System.out.println("Minimum weighted distance: " + result);
    }
}
