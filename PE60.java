import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** Prime pair sets. */
public class PE60 {

    /** The seed of primes. */
    public static ArrayList<Integer> primes = new ArrayList<>(Arrays.asList(2,
                3, 5, 7));

    /** A map of prime pairs. */
    public static HashMap<Integer, ArrayList<Integer>> pairPrimes =
        new HashMap<>();

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

    /** Returns whether or not A and B are a prime pair. Assumes that A and B
     *  are primes. */
    public static boolean isPrimePair(int a, int b) {
        String A = Integer.toString(a), B = Integer.toString(b);
        return isPrime(Integer.parseInt(A+B)) && isPrime(Integer.parseInt(B+A));
    }

    /** Returns a sequence of prime pairs starting from INDEX. */
    public static ArrayList<Integer> traverse(int index, int length,
            ArrayList<Integer> check) {
        int n = primes.get(index);
        for (int c : check) {
            if (!isPrimePair(c, n)) {
                return new ArrayList<Integer>();
            }
        }
        ArrayList<Integer> res = new ArrayList<>(check);
        res.add(n);
        if (length == 1) {
            return res;
        }
        int i = 0;
        ArrayList<Integer> pairs = pairPrimes.get(n);
        while (i < pairs.size()) {
            ArrayList<Integer> newres = traverse(pairs.get(i), length - 1, res);
            if (newres.size() > 0) {
                res.add(pairs.get(i));
                return res;
            }
            i++;
        }
        return new ArrayList<Integer>();
    }

    /** Main program. */
	public static void main(String[] args) {
        isPrime(1000);
        int size = primes.size();
        for (int i = size - 1; i >= 0; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                int a = primes.get(i), b = primes.get(j);
                if (isPrimePair(a, b)) {
                    if (!pairPrimes.containsKey(a)) {
                        pairPrimes.put(a, new ArrayList<Integer>());
                    }
                    pairPrimes.get(a).add(b);
                }
            }
        }
	}
}
