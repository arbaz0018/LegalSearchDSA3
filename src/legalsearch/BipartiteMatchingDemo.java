
package legalsearch;

import java.util.ArrayList;
import java.util.List;

public class BipartiteMatchingDemo {

    private final int cases;
    private final int lawyers;
    private final List<Integer>[] graph;
    private final int[] lawyerMatch;

    @SuppressWarnings("unchecked")
    public BipartiteMatchingDemo(int cases, int lawyers) {
        this.cases = cases;
        this.lawyers = lawyers;

        graph = new ArrayList[cases];

        for (int i = 0; i < cases; i++) {
            graph[i] = new ArrayList<>();
        }

        lawyerMatch = new int[lawyers];
        java.util.Arrays.fill(lawyerMatch, -1);
    }

    // Add a possible case-to-lawyer assignment
    public void addOption(int caseId, int lawyerId) {
        graph[caseId].add(lawyerId);
    }

    private boolean tryMatch(int caseId, boolean[] visited) {
        for (int lawyerId : graph[caseId]) {
            if (visited[lawyerId]) {
                continue;
            }

            visited[lawyerId] = true;

            if (lawyerMatch[lawyerId] == -1
                    || tryMatch(lawyerMatch[lawyerId], visited)) {

                lawyerMatch[lawyerId] = caseId;
                return true;
            }
        }

        return false;
    }

    public int maximumMatching() {
        int matched = 0;

        for (int caseId = 0; caseId < cases; caseId++) {
            boolean[] visited = new boolean[lawyers];

            if (tryMatch(caseId, visited)) {
                matched++;
            }
        }

        return matched;
    }

    public void printAssignments() {
        for (int lawyerId = 0; lawyerId < lawyers; lawyerId++) {
            if (lawyerMatch[lawyerId] != -1) {
                System.out.println(
                    "Case " + (lawyerMatch[lawyerId] + 1)
                    + " -> Lawyer " + (lawyerId + 1)
                );
            }
        }
    }

    public static void main(String[] args) {
        BipartiteMatchingDemo matching =
                new BipartiteMatchingDemo(4, 3);

        matching.addOption(0, 0);
        matching.addOption(0, 1);
        matching.addOption(1, 0);
        matching.addOption(2, 1);
        matching.addOption(2, 2);
        matching.addOption(3, 2);

        System.out.println("LegalSearch - Bipartite Matching Demo");

        int result = matching.maximumMatching();

        matching.printAssignments();

        System.out.println("Maximum matched cases: " + result);
    }
}
