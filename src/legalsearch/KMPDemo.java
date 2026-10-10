
package legalsearch;

public class KMPDemo {

    public static int search(String text, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            return 0;
        }

        if (text == null || pattern.length() > text.length()) {
            return -1;
        }

        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                lps[i++] = ++length;
            } else if (length > 0) {
                length = lps[length - 1];
            } else {
                lps[i++] = 0;
            }
        }

        i = 0;
        int j = 0;

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                return i - j;
            } else if (i < text.length()
                    && text.charAt(i) != pattern.charAt(j)) {
                if (j > 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String text =
                "Indian law covers contracts, fraud and legal disputes.";

        String pattern = "fraud";

        System.out.println("LegalSearch - KMP Demo");
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
