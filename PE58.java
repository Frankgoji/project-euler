import java.util.ArrayList;
import java.util.Arrays;

/** Spiral primes: */
public class PE58 {

    /** The seed of primes. */
    public static ArrayList<Integer> primes = new ArrayList<>(Arrays.asList(2,
                3, 5, 7));

    /** Returns whether or not X is prime. */
    public static boolean isPrime(int x) {
        if (primes.contains(x)) {
            return true;
        } else if (x < primes.get(primes.size() - 1)) {
            return false;
        }
        for (int i = primes.get(primes.size() - 1) + 1; i < x + 1; i++) {
            for (int n : primes) {
                if (i % n == 0) {
                    break;
                } else if (n * n > i) {
                    primes.add(i);
                    break;
                }
            }
        }
        return primes.get(primes.size() - 1) == x;
    }

    /** Main program. */
	public static void main(String[] args) {
        int iteration = 2, size = 5, prev1 = 7, prev2 = 9;
        double numPrimes = 3.0;
        while (numPrimes / size > 0.1) {
            int a1 = prev1 + iteration * 4 - 2, b1 = a1 + iteration * 4,
                a2 = prev2 + iteration * 4, b2 = a2 + iteration * 4;
            prev1 = b1; prev2 = b2;
            int[] check = {a1, a2, b1, b2};
            for (int i : check) {
                if (isPrime(i)) numPrimes++;
            }
            size += 4;
            iteration += 1;
        }
        System.out.println(iteration * 2 - 1);
	}
}
