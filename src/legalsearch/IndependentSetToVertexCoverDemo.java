
package legalsearch;

import java.util.HashSet;
import java.util.Set;

public class IndependentSetToVertexCoverDemo {

    // Check whether a selected set is independent.
    public static boolean isIndependentSet(
            int[][] edges, Set<Integer> independentSet) {

        for (int[] edge : edges) {
            if (independentSet.contains(edge[0])
                    && independentSet.contains(edge[1])) {
                return false;
            }
        }

        return true;
    }

    // Check whether every edge touches the vertex cover.
    public static boolean isVertexCover(
            int[][] edges, Set<Integer> vertexCover) {

        for (int[] edge : edges) {
            if (!vertexCover.contains(edge[0])
                    && !vertexCover.contains(edge[1])) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        // Undirected graph with vertices 0, 1, 2, 3
        int[][] edges = {
            {0, 1},
            {0, 2},
            {1, 2},
            {1, 3},
            {2, 3}
        };

        Set<Integer> independentSet = new HashSet<>();
        independentSet.add(0);
        independentSet.add(3);

        Set<Integer> vertexCover = new HashSet<>();

        // Construct the complement of the independent set.
        for (int vertex = 0; vertex < 4; vertex++) {
            if (!independentSet.contains(vertex)) {
                vertexCover.add(vertex);
            }
        }

        System.out.println(
            "LegalSearch - Independent Set to Vertex Cover Demo"
        );

        System.out.println("Independent set: " + independentSet);
        System.out.println("Is independent set: "
                + isIndependentSet(edges, independentSet));

        System.out.println("Complement vertex cover: " + vertexCover);
        System.out.println("Is vertex cover: "
                + isVertexCover(edges, vertexCover));

        System.out.println(
            "Reduction verified: "
            + (isIndependentSet(edges, independentSet)
                && isVertexCover(edges, vertexCover))
        );
    }
}
