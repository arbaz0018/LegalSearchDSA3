
package legalsearch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class LegalSearchApp {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMainMenu();

            int choice = readChoice("Enter your choice: ");

            switch (choice) {
                case 1:
                    showSubmenu("CO1 - Pattern Matching", new String[][] {
                        {"KMP", "KMPDemo"},
                        {"Z-Function", "ZFunctionDemo"},
                        {"Rabin-Karp", "RabinKarpDemo"},
                        {"Aho-Corasick", "AhoCorasickDemo"}
                    });
                    break;

                case 2:
                    showSubmenu("CO2 - Suffix Structures", new String[][] {
                        {"Suffix Array and LCP", "SuffixArrayIndex"},
                        {"Suffix Array Search", "SuffixArraySearch"},
                        {"Suffix Automaton", "SuffixAutomaton"},
                        {"Suffix Tree / Trie Demo", "SuffixTreeDemo"},
                        {"SA-IS", "SAISDemo"}
                    });
                    break;

                case 3:
                    showSubmenu("CO3 - Dynamic Programming", new String[][] {
                        {"Levenshtein Distance", "LevenshteinDemo"},
                        {"Damerau-Levenshtein Distance",
                            "DamerauLevenshteinDemo"},
                        {"Weighted Edit Distance", "WeightedEditDistanceDemo"},
                        {"Bitmask DP", "BitmaskDPDemo"},
                        {"Matrix Chain Multiplication", "MatrixChainDemo"}
                    });
                    break;

                case 4:
                    showSubmenu("CO4 - Network Flow", new String[][] {
                        {"Ford-Fulkerson", "FordFulkersonDemo"},
                        {"Edmonds-Karp", "EdmondsKarpDemo"},
                        {"Dinic", "DinicDemo"},
                        {"Bipartite Matching", "BipartiteMatchingDemo"},
                        {"Konig's Theorem", "KonigTheoremDemo"}
                    });
                    break;

                case 5:
                    showSubmenu("CO5 - NP-Completeness & Approximation",
                        new String[][] {
                            {"3-SAT", "ThreeSATDemo"},
                            {"3-SAT to CLIQUE", "ThreeSATToCliqueDemo"},
                            {"CLIQUE to Independent Set",
                                "CliqueToIndependentSetDemo"},
                            {"Independent Set to Vertex Cover",
                                "IndependentSetToVertexCoverDemo"},
                            {"Vertex Cover 2-Approximation",
                                "VertexCoverApproximationDemo"}
                        });
                    break;

                case 6:
                    showSubmenu("CO6 - Randomized & Parallel Algorithms",
                        new String[][] {
                            {"Randomized QuickSort", "RandomizedQuickSortDemo"},
                            {"Reservoir Sampling", "ReservoirSamplingDemo"},
                            {"Miller-Rabin", "MillerRabinDemo"},
                            {"Blelloch Scan", "BlellochScanDemo"},
                            {"Parallel Reduce", "ParallelReduceDemo"},
                            {"Brent's Theorem", "BrentsTheoremDemo"}
                        });
                    break;

                case 0:
                    System.out.println("Exiting LegalSearch. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Enter a number from 0 to 6.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("       LEGALSEARCH - DSA PROJECT");
        System.out.println("========================================");
        System.out.println("1. CO1 - Pattern Matching");
        System.out.println("2. CO2 - Suffix Structures");
        System.out.println("3. CO3 - Dynamic Programming");
        System.out.println("4. CO4 - Network Flow");
        System.out.println("5. CO5 - NP-Completeness & Approximation");
        System.out.println("6. CO6 - Randomized & Parallel Algorithms");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    private static void showSubmenu(
            String title, String[][] algorithms) {

        while (true) {
            System.out.println("\n========== " + title + " ==========");

            for (int i = 0; i < algorithms.length; i++) {
                System.out.println((i + 1) + ". " + algorithms[i][0]);
            }

            System.out.println("0. Back to Main Menu");

            int choice = readChoice("Choose an algorithm: ");

            if (choice == 0) {
                return;
            }

            if (choice < 1 || choice > algorithms.length) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            runDemo(algorithms[choice - 1][1]);

            System.out.println("\nPress Enter to return to the submenu...");
            scanner.nextLine();
        }
    }

    private static int readChoice(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void runDemo(String className) {
        try {
            Class<?> demoClass = Class.forName("legalsearch." + className);

            Method mainMethod =
                    demoClass.getMethod("main", String[].class);

            System.out.println("\n--- Running "
                    + demoClass.getSimpleName() + " ---\n");

            mainMethod.invoke(null, (Object) new String[0]);

            System.out.println("\n--- Finished "
                    + demoClass.getSimpleName() + " ---");

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Class not found: " + className);
            System.out.println("Check that the class exists in package legalsearch.");

        } catch (NoSuchMethodException e) {
            System.out.println("ERROR: " + className
                    + " does not have a public main method.");

        } catch (IllegalAccessException e) {
            System.out.println("ERROR: Cannot access " + className);

        } catch (InvocationTargetException e) {
            System.out.println("ERROR while executing " + className);

            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }
}
