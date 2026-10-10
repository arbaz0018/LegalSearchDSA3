
package legalsearch;

import java.util.HashMap;
import java.util.Map;

public class SuffixTreeDemo {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean isEnd;
    }

    private final Node root = new Node();

    // Insert a suffix into the tree
    public void insertSuffix(String suffix) {
        Node current = root;

        for (char ch : suffix.toCharArray()) {
            current.children.putIfAbsent(ch, new Node());
            current = current.children.get(ch);
        }

        current.isEnd = true;
    }

    // Build a simple suffix trie from the text
    public void build(String text) {
        for (int i = 0; i < text.length(); i++) {
            insertSuffix(text.substring(i) + "$");
        }
    }

    // Check whether a pattern occurs in the text
    public boolean containsSubstring(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }

        Node current = root;

        for (char ch : pattern.toCharArray()) {
            Node next = current.children.get(ch);

            if (next == null) {
                return false;
            }

            current = next;
        }

        return true;
    }

    public static void main(String[] args) {
        String text = "banana";

        SuffixTreeDemo tree = new SuffixTreeDemo();
        tree.build(text);

        String[] patterns = {
            "ana", "banana", "nan", "apple", "ban"
        };

        System.out.println("LegalSearch - Suffix Tree Demo");
        System.out.println("Text: " + text);
        System.out.println();

        for (String pattern : patterns) {
            System.out.println(
                pattern + " -> "
                + (tree.containsSubstring(pattern)
                    ? "FOUND" : "NOT FOUND")
            );
        }
    }
}
