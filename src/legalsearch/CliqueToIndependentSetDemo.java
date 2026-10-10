
package legalsearch;

public class CliqueToIndependentSetDemo {

    // Build the complement graph.
    public static boolean[][] complement(boolean[][] graph) {
        int n = graph.length;
        boolean[][] result = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    result[i][j] = !graph[i][j];
                }
            }
        }

        return result;
    }

    // Check whether selected vertices form an independent set.
    public static boolean isIndependentSet(
            boolean[][] graph, int[] vertices) {

        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                if (graph[vertices[i]][vertices[j]]) {
                    return false;
                }
            }
        }

        return true;
    }

    // Check whether selected vertices form a clique.
    public static boolean isClique(
            boolean[][] graph, int[] vertices) {

        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                if (!graph[vertices[i]][vertices[j]]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {

        // Example graph with a clique on vertices 0, 1, and 2.
        boolean[][] graph = {
            {false, true,  true,  false},
            {true,  false, true,  false},
            {true,  true,  false, false},
            {false, false, false, false}
        };

        int[] selected = {0, 1, 2};

        boolean[][] complementGraph = complement(graph);

        System.out.println(
            "LegalSearch - CLIQUE to Independent Set Demo"
        );

        System.out.println(
            "Original graph clique {0, 1, 2}: "
            + isClique(graph, selected)
        );

        System.out.println(
            "Complement graph independent set {0, 1, 2}: "
            + isIndependentSet(complementGraph, selected)
        );

        System.out.println(
            "Reduction verified: "
            + (isClique(graph, selected)
                == isIndependentSet(complementGraph, selected))
        );
    }
}
