
package legalsearch;

import java.util.*;

public class AhoCorasickDemo {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node failure;
        List<String> outputs = new ArrayList<>();
    }

    private final Node root = new Node();

    public AhoCorasickDemo(String[] patterns) {
        buildTrie(patterns);
        buildFailureLinks();
    }

    private void buildTrie(String[] patterns) {
        for (String pattern : patterns) {
            if (pattern == null || pattern.isEmpty()) {
                continue;
            }

            Node current = root;

            for (char ch : pattern.toCharArray()) {
                current.children.putIfAbsent(ch, new Node());
                current = current.children.get(ch);
            }

            current.outputs.add(pattern);
        }
    }

    private void buildFailureLinks() {
        Queue<Node> queue = new LinkedList<>();
        root.failure = root;

        for (Node child : root.children.values()) {
            child.failure = root;
            queue.offer(child);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry
                    : current.children.entrySet()) {

                char ch = entry.getKey();
                Node child = entry.getValue();
                Node fallback = current.failure;

                while (fallback != root
                        && !fallback.children.containsKey(ch)) {
                    fallback = fallback.failure;
                }

                if (fallback.children.containsKey(ch)) {
                    child.failure = fallback.children.get(ch);
                } else {
                    child.failure = root;
                }

                child.outputs.addAll(child.failure.outputs);
                queue.offer(child);
            }
        }
    }

    public void search(String text) {
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            while (current != root
                    && !current.children.containsKey(ch)) {
                current = current.failure;
            }

            if (current.children.containsKey(ch)) {
                current = current.children.get(ch);
            }

            for (String pattern : current.outputs) {
                int start = i - pattern.length() + 1;

                System.out.println(
                        "Pattern \"" + pattern
                        + "\" found at index " + start);
            }
        }
    }

    public static void main(String[] args) {
        String text =
                "Indian law covers contracts, fraud and legal disputes.";

        String[] patterns = {
            "law", "contracts", "fraud", "legal"
        };

        AhoCorasickDemo matcher = new AhoCorasickDemo(patterns);

        System.out.println("LegalSearch - Aho-Corasick Demo");
        System.out.println("Text: " + text);
        System.out.println("Patterns: " + Arrays.toString(patterns));
        System.out.println("Matches:");

        matcher.search(text);
    }
}
