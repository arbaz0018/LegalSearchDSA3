
package legalsearch;

import java.util.HashMap;
import java.util.Map;

public class SuffixAutomaton {

    static class State {
        int length;
        int link = -1;
        Map<Character, Integer> next = new HashMap<>();
    }

    private final State[] states;
    private int size;
    private int last;

    public SuffixAutomaton(String text) {
        states = new State[2 * text.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State();
        }

        size = 1;
        last = 0;

        for (char ch : text.toCharArray()) {
            extend(ch);
        }
    }

    private void extend(char ch) {
        int current = size++;
        states[current].length = states[last].length + 1;

        int p = last;

        while (p != -1 && !states[p].next.containsKey(ch)) {
            states[p].next.put(ch, current);
            p = states[p].link;
        }

        if (p == -1) {
            states[current].link = 0;
        } else {
            int q = states[p].next.get(ch);

            if (states[p].length + 1 == states[q].length) {
                states[current].link = q;
            } else {
                int clone = size++;
                states[clone].length = states[p].length + 1;
                states[clone].link = states[q].link;
                states[clone].next.putAll(states[q].next);

                while (p != -1
                        && states[p].next.getOrDefault(ch, -1) == q) {
                    states[p].next.put(ch, clone);
                    p = states[p].link;
                }

                states[q].link = clone;
                states[current].link = clone;
            }
        }

        last = current;
    }

    public boolean containsSubstring(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return false;
        }

        int current = 0;

        for (char ch : pattern.toCharArray()) {
            Integer nextState = states[current].next.get(ch);

            if (nextState == null) {
                return false;
            }

            current = nextState;
        }

        return true;
    }

    public static void main(String[] args) {
        String document =
            "Indian law covers contracts, fraud and legal disputes.";

        SuffixAutomaton automaton = new SuffixAutomaton(document);

        String[] patterns = {
            "contracts",
            "fraud",
            "legal",
            "criminal",
            "disputes"
        };

        System.out.println("LegalSearch - Suffix Automaton Demo");
        System.out.println("Document: " + document);
        System.out.println();

        for (String pattern : patterns) {
            System.out.println(
                pattern + " -> "
                + (automaton.containsSubstring(pattern)
                    ? "FOUND" : "NOT FOUND")
            );
        }
    }
}
