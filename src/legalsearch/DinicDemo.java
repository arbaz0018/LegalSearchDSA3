
package legalsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DinicDemo {

    static class Edge {
        int to;
        int reverse;
        int capacity;

        Edge(int to, int reverse, int capacity) {
            this.to = to;
            this.reverse = reverse;
            this.capacity = capacity;
        }
    }

    private final List<Edge>[] graph;
    private final int[] level;
    private final int[] nextEdge;
    private final int vertices;

    @SuppressWarnings("unchecked")
    public DinicDemo(int vertices) {
        this.vertices = vertices;
        graph = new ArrayList[vertices];

        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
        }

        level = new int[vertices];
        nextEdge = new int[vertices];
    }

    public void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(
            to, graph[to].size(), capacity
        );

        Edge reverse = new Edge(
            from, graph[from].size(), 0
        );

        graph[from].add(forward);
        graph[to].add(reverse);
    }

    // Build the level graph using BFS
    private boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);

        int[] queue = new int[vertices];
        int front = 0;
        int rear = 0;

        queue[rear++] = source;
        level[source] = 0;

        while (front < rear) {
            int u = queue[front++];

            for (Edge edge : graph[u]) {
                if (edge.capacity > 0
                        && level[edge.to] == -1) {
                    level[edge.to] = level[u] + 1;
                    queue[rear++] = edge.to;
                }
            }
        }

        return level[sink] != -1;
    }

    // Send flow through the level graph using DFS
    private int dfs(int u, int sink, int flow) {
        if (u == sink) {
            return flow;
        }

        for (; nextEdge[u] < graph[u].size();
                nextEdge[u]++) {

            Edge edge = graph[u].get(nextEdge[u]);

            if (edge.capacity > 0
                    && level[edge.to] == level[u] + 1) {

                int pushed = dfs(
                    edge.to,
                    sink,
                    Math.min(flow, edge.capacity)
                );

                if (pushed > 0) {
                    edge.capacity -= pushed;

                    graph[edge.to]
                        .get(edge.reverse).capacity += pushed;

                    return pushed;
                }
            }
        }

        return 0;
    }

    public int maxFlow(int source, int sink) {
        int totalFlow = 0;
        int phases = 0;

        while (bfs(source, sink)) {
            phases++;
            Arrays.fill(nextEdge, 0);

            int pushed;

            while ((pushed = dfs(
                    source, sink, Integer.MAX_VALUE)) > 0) {
                totalFlow += pushed;
            }

            System.out.println(
                "Level graph phase " + phases
                + " completed. Total flow: " + totalFlow
            );
        }

        return totalFlow;
    }

    public static void main(String[] args) {
        DinicDemo network = new DinicDemo(6);

        network.addEdge(0, 1, 16);
        network.addEdge(0, 2, 13);
        network.addEdge(1, 2, 10);
        network.addEdge(2, 1, 4);
        network.addEdge(1, 3, 12);
        network.addEdge(3, 2, 9);
        network.addEdge(2, 4, 14);
        network.addEdge(4, 3, 7);
        network.addEdge(3, 5, 20);
        network.addEdge(4, 5, 4);

        System.out.println("LegalSearch - Dinic's Algorithm Demo");
        System.out.println("Source: 0");
        System.out.println("Sink: 5");

        int result = network.maxFlow(0, 5);

        System.out.println("Maximum flow: " + result);
    }
}
