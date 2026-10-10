
package legalsearch;

public class ThreeSATDemo {

    // Literal format:
    // 1 = x1, -1 = NOT x1
    // 2 = x2, -2 = NOT x2, etc.
    // Each row represents one 3-literal clause.
    public static boolean evaluate(
            int[][] clauses, boolean[] assignment) {

        for (int[] clause : clauses) {
            boolean clauseTrue = false;

            for (int literal : clause) {
                int variable = Math.abs(literal) - 1;
                boolean value = assignment[variable];

                if (literal < 0) {
                    value = !value;
                }

                if (value) {
                    clauseTrue = true;
                    break;
                }
            }

            if (!clauseTrue) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        // Formula:
        // (x1 OR NOT x2 OR x3)
        // AND (NOT x1 OR x2 OR x3)
        // AND (x1 OR x2 OR NOT x3)

        int[][] clauses = {
            {1, -2, 3},
            {-1, 2, 3},
            {1, 2, -3}
        };

        boolean[] assignment = {
            true, true, true
        };

        System.out.println("LegalSearch - 3-SAT Demo");
        System.out.println("Assignment: x1=true, x2=true, x3=true");

        for (int i = 0; i < clauses.length; i++) {
            boolean clauseTrue = false;

            for (int literal : clauses[i]) {
                int variable = Math.abs(literal) - 1;
                boolean value = assignment[variable];

                if (literal < 0) {
                    value = !value;
                }

                if (value) {
                    clauseTrue = true;
                    break;
                }
            }

            System.out.println(
                "Clause " + (i + 1) + ": "
                + (clauseTrue ? "TRUE" : "FALSE")
            );
        }

        System.out.println(
            "Formula result: "
            + (evaluate(clauses, assignment)
                ? "SATISFIABLE for this assignment"
                : "FALSE for this assignment")
        );
    }
}
