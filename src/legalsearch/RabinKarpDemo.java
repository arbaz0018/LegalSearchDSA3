
package legalsearch;

public class RabinKarpDemo {

    private static final int BASE = 256;
    private static final int MOD = 1_000_000_007;

    public static int search(String text, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            return 0;
        }

        if (text == null || pattern.length() > text.length()) {
            return -1;
        }

        int m = pattern.length();
        int n = text.length();

        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1;

        for (int i = 0; i < m - 1; i++) {
            highestPower = (highestPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MOD;
            windowHash = (windowHash * BASE + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == windowHash
                    && text.regionMatches(i, pattern, 0, m)) {
                return i;
            }

            if (i < n - m) {
                windowHash =
                        (windowHash - text.charAt(i) * highestPower % MOD + MOD)
                        % MOD;

                windowHash =
                        (windowHash * BASE + text.charAt(i + m)) % MOD;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String text =
                "Indian law covers contracts, fraud and legal disputes.";
        String pattern = "contracts";

        System.out.println("LegalSearch - Rabin-Karp Demo");
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);

        int index = search(text, pattern);

        if (index >= 0) {
            System.out.println("Pattern found at index: " + index);
        } else {
            System.out.println("Pattern not found.");
        }
    }
}
