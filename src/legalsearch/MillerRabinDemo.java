
package legalsearch;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinDemo {

    public static boolean isProbablyPrime(
            long number, int rounds) {

        if (number < 2 || rounds < 1) {
            return false;
        }

        if (number == 2 || number == 3) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        // Write number - 1 as d * 2^s, where d is odd.
        long d = number - 1;
        int s = 0;

        while (d % 2 == 0) {
            d /= 2;
            s++;
        }

        BigInteger n = BigInteger.valueOf(number);
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger exponent = BigInteger.valueOf(d);
        BigInteger two = BigInteger.valueOf(2);
        Random random = new Random();

        for (int round = 0; round < rounds; round++) {

            // Choose a random base a in [2, number - 2].
            BigInteger a = new BigInteger(
                    n.bitLength(), random);

            while (a.compareTo(two) < 0
                    || a.compareTo(nMinusOne) >= 0) {
                a = new BigInteger(n.bitLength(), random);
            }

            BigInteger x = a.modPow(exponent, n);

            if (x.equals(BigInteger.ONE)
                    || x.equals(nMinusOne)) {
                continue;
            }

            boolean passed = false;

            for (int r = 1; r < s; r++) {
                x = x.multiply(x).mod(n);

                if (x.equals(nMinusOne)) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        long[] numbers = {
            2, 17, 97, 101, 221, 1001
        };

        System.out.println(
                "LegalSearch - Miller-Rabin Demo");

        for (long number : numbers) {
            System.out.println(number + " -> "
                    + (isProbablyPrime(number, 10)
                    ? "Probably Prime"
                    : "Composite"));
        }
    }
}
