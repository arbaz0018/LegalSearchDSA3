
package legalsearch;

import java.util.ArrayList;
import java.util.List;

public class VertexCoverApproximationDemo {

    public static List<Integer> approximateVertexCover(
            int vertices, int[][] edges) {

        boolean[] covered = new boolean[vertices];
        List<Integer> cover = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // If this edge is not already covered,
            // select both endpoints.
            if (!covered[u] && !covered[v]) {
                covered[u] = true;
                covered[v] = true;

                if (!cover.contains(u)) {
                    cover.add(u);
                }

                if (!cover.contains(v)) {
                    cover.add(v);
                }
            }
        }

        return cover;
    }

    public static boolean isVertexCover(
            int[][] edges, List<Integer> cover) {

        for (int[] edge : edges) {
            if (!cover.contains(edge[0])
                    && !cover.contains(edge[1])) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        int vertices = 5;

        int[][] edges = {
            {0, 1},
            {0, 2},
            {1, 2},
            {1, 3},
            {2, 4},
            {3, 4}
        };

        List<Integer> cover =
                approximateVertexCover(vertices, edges);

        System.out.println(
                "LegalSearch - Vertex Cover 2-Approximation Demo");

        System.out.println("Selected vertex cover: " + cover);

        System.out.println("Vertex cover is valid: "
                + isVertexCover(edges, cover));

        System.out.println("Number of selected vertices: "
                + cover.size());

        System.out.println(
                "Approximation guarantee: at most 2 times optimal");
    }
}
