
package legalsearch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarpDemo {

    private static final int V = 6;

    // BFS finds the shortest augmenting path by edge count.
    private static boolean bfs(
            int[][] residual,
            int source,
            int sink,
            int[] parent) {

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && residual[u][v] > 0) {
                    parent[v] = u;
                    visited[v] = true;

                    if (v == sink) {
                        return true;
                    }

                    queue.add(v);
                }
            }
        }

        return false;
    }

    public static int maxFlow(
            int[][] capacity,
            int source,
            int sink) {

        int[][] residual = new int[V][V];

        for (int i = 0; i < V; i++) {
            residual[i] = Arrays.copyOf(capacity[i], V);
        }

        int[] parent = new int[V];
        int maxFlow = 0;
        int augmentations = 0;

        while (bfs(residual, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            // Find bottleneck capacity
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residual[u][v]);
            }

            // Update forward and reverse residual capacities
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
            augmentations++;

            System.out.println(
                "Augmentation " + augmentations
                + " | Flow added: " + pathFlow
            );
        }

        System.out.println(
            "Total augmenting paths: " + augmentations
        );

        return maxFlow;
    }

    public static void main(String[] args) {

        int[][] capacity = {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };

        int source = 0;
        int sink = 5;

        System.out.println("LegalSearch - Edmonds-Karp Demo");
        System.out.println("Source: " + source);
        System.out.println("Sink: " + sink);

        int result = maxFlow(capacity, source, sink);

        System.out.println("Maximum flow: " + result);
    }
}
