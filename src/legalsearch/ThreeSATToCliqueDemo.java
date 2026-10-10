
package legalsearch;

import java.util.ArrayList;
import java.util.List;

public class ThreeSATToCliqueDemo {

    static class LiteralNode {
        int clause;
        int literal;

        LiteralNode(int clause, int literal) {
            this.clause = clause;
            this.literal = literal;
        }

        @Override
        public String toString() {
            int variable = Math.abs(literal);
            return (literal < 0 ? "NOT x" : "x")
                    + variable + " (Clause " + (clause + 1) + ")";
        }
    }

    public static void main(String[] args) {

        // Formula:
        // C1 = (x1 OR NOT x2 OR x3)
        // C2 = (NOT x1 OR x2 OR x3)
        // C3 = (x1 OR x2 OR NOT x3)

        int[][] clauses = {
            {1, -2, 3},
            {-1, 2, 3},
            {1, 2, -3}
        };

        List<LiteralNode> nodes = new ArrayList<>();

        for (int c = 0; c < clauses.length; c++) {
            for (int literal : clauses[c]) {
                nodes.add(new LiteralNode(c, literal));
            }
        }

        // Connect literals from different clauses
        // unless they contradict each other.
        boolean[][] graph = new boolean[nodes.size()][nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                LiteralNode a = nodes.get(i);
                LiteralNode b = nodes.get(j);

                boolean differentClauses =
                        a.clause != b.clause;

                boolean notContradictory =
                        a.literal != -b.literal;

                if (differentClauses && notContradictory) {
                    graph[i][j] = true;
                    graph[j][i] = true;
                }
            }
        }

        System.out.println("LegalSearch - 3-SAT to CLIQUE Demo");
        System.out.println("Number of clauses: " + clauses.length);
        System.out.println("Number of graph vertices: " + nodes.size());
        System.out.println("Target clique size: " + clauses.length);

        // Find a clique by selecting one compatible literal
        // from each clause.
        List<Integer> selected = new ArrayList<>();

        if (findClique(nodes, graph, 0, selected, clauses.length)) {
            System.out.println("Clique of target size found:");

            for (int index : selected) {
                System.out.println(nodes.get(index));
            }

            System.out.println("CLIQUE result: YES");
        } else {
            System.out.println("CLIQUE result: NO");
        }
    }

    private static boolean findClique(
            List<LiteralNode> nodes,
            boolean[][] graph,
            int clause,
            List<Integer> selected,
            int target) {

        if (selected.size() == target) {
            return true;
        }

        if (clause >= target) {
            return false;
        }

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).clause != clause) {
                continue;
            }

            boolean compatible = true;

            for (int chosen : selected) {
                if (!graph[i][chosen]) {
                    compatible = false;
                    break;
                }
            }

            if (compatible) {
                selected.add(i);

                if (findClique(
                        nodes, graph, clause + 1, selected, target)) {
                    return true;
                }

                selected.remove(selected.size() - 1);
            }
        }

        return false;
    }
}
