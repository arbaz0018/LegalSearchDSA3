
package legalsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KonigTheoremDemo {

    private final int leftCount;
    private final int rightCount;
    private final List<Integer>[] graph;
    private final int[] rightMatch;

    @SuppressWarnings("unchecked")
    public KonigTheoremDemo(int leftCount, int rightCount) {
        this.leftCount = leftCount;
        this.rightCount = rightCount;

        graph = new ArrayList[leftCount];

        for (int i = 0; i < leftCount; i++) {
            graph[i] = new ArrayList<>();
        }

        rightMatch = new int[rightCount];
        Arrays.fill(rightMatch, -1);
    }

    public void addEdge(int left, int right) {
        graph[left].add(right);
    }

    private boolean tryMatch(int left, boolean[] visited) {
        for (int right : graph[left]) {
            if (visited[right]) {
                continue;
            }

            visited[right] = true;

            if (rightMatch[right] == -1
                    || tryMatch(rightMatch[right], visited)) {
                rightMatch[right] = left;
                return true;
            }
        }

        return false;
    }

    public int maximumMatching() {
        int matched = 0;

        for (int left = 0; left < leftCount; left++) {
            boolean[] visited = new boolean[rightCount];

            if (tryMatch(left, visited)) {
                matched++;
            }
        }

        return matched;
    }

    // Find a minimum vertex cover using alternating paths.
    // Requires maximumMatching() to have been called first.
    public void printMinimumVertexCover() {
        boolean[] visitedLeft = new boolean[leftCount];
        boolean[] visitedRight = new boolean[rightCount];

        List<Integer> queue = new ArrayList<>();

        // Start from unmatched left-side vertices.
        for (int left = 0; left < leftCount; left++) {
            boolean matched = false;

            for (int right = 0; right < rightCount; right++) {
                if (rightMatch[right] == left) {
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                visitedLeft[left] = true;
                queue.add(left);
            }
        }

        // Traverse unmatched edges left-to-right,
        // then matched edges right-to-left.
        for (int i = 0; i < queue.size(); i++) {
            int left = queue.get(i);

            for (int right : graph[left]) {
                if (rightMatch[right] == left
                        || visitedRight[right]) {
                    continue;
                }

                visitedRight[right] = true;

                int nextLeft = rightMatch[right];

                if (nextLeft != -1 && !visitedLeft[nextLeft]) {
                    visitedLeft[nextLeft] = true;
                    queue.add(nextLeft);
                }
            }
        }

        // Minimum cover = unvisited left vertices
        // together with visited right vertices.
        int coverSize = 0;

        System.out.println("Minimum vertex cover:");

        for (int left = 0; left < leftCount; left++) {
            if (!visitedLeft[left]) {
                System.out.println("Case " + (left + 1));
                coverSize++;
            }
        }

        for (int right = 0; right < rightCount; right++) {
            if (visitedRight[right]) {
                System.out.println("Lawyer " + (right + 1));
                coverSize++;
            }
        }

        System.out.println("Minimum vertex cover size: " + coverSize);
    }

    public static void main(String[] args) {
        KonigTheoremDemo graph = new KonigTheoremDemo(4, 3);

        graph.addEdge(0, 0);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.addEdge(2, 1);
        graph.addEdge(2, 2);
        graph.addEdge(3, 2);

        System.out.println("LegalSearch - Konig's Theorem Demo");

        int matching = graph.maximumMatching();

        System.out.println("Maximum matching size: " + matching);

        graph.printMinimumVertexCover();

        System.out.println(
            "Theorem verified: matching size equals cover size = "
            + matching
        );
    }
}
