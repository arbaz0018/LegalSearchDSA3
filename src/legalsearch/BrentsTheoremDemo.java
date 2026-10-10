
package legalsearch;

public class BrentsTheoremDemo {

    public static void main(String[] args) {

        int work = 100;
        int criticalPath = 20;
        int[] processors = {1, 2, 4, 5, 10};

        System.out.println(
                "LegalSearch - Brent's Theorem Demo");

        System.out.println("Total work (W): " + work);
        System.out.println(
                "Critical path (D): " + criticalPath);

        System.out.println("\nProcessor count | Time upper bound");

        for (int p : processors) {
            double timeBound =
                    (double) work / p + criticalPath;

            System.out.printf(
                    "%15d | %.2f%n", p, timeBound);
        }

        System.out.println(
                "\nFormula: T_P <= W/P + D");
    }
}
