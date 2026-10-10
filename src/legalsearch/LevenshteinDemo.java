
package legalsearch;

public class LevenshteinDemo {

    // Calculate the minimum number of insertions,
    // deletions, and substitutions needed to transform
    // one string into another.
    public static int levenshteinDistance(
            String first, String second) {

        int m = first.length();
        int n = second.length();

        int[][] dp = new int[m + 1][n + 1];

        // Convert first string into an empty string
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Convert an empty string into second string
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Fill the dynamic programming table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                int cost = first.charAt(i - 1)
                        == second.charAt(j - 1) ? 0 : 1;

                int deletion = dp[i - 1][j] + 1;
                int insertion = dp[i][j - 1] + 1;
                int substitution = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(
                    Math.min(deletion, insertion),
                    substitution
                );
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {

        String[] legalKeywords = {
            "contract",
            "criminal",
            "fraud",
            "property",
            "court"
        };

        String search = "contrct";

        System.out.println("LegalSearch - Levenshtein Demo");
        System.out.println("Search keyword: " + search);
        System.out.println("\nClosest legal keywords:");

        for (String keyword : legalKeywords) {
            int distance =
                    levenshteinDistance(search, keyword);

            System.out.println(
                keyword + " | Edit distance: " + distance
            );
        }

        // Independent verification tests
        System.out.println("\nDistance verification:");

        System.out.println("contrct -> contract: "
                + levenshteinDistance("contrct", "contract"));

        System.out.println("contrct -> criminal: "
                + levenshteinDistance("contrct", "criminal"));

        System.out.println("contrct -> fraud: "
                + levenshteinDistance("contrct", "fraud"));

        System.out.println("contrct -> property: "
                + levenshteinDistance("contrct", "property"));

        System.out.println("contrct -> court: "
                + levenshteinDistance("contrct", "court"));
    }
}
