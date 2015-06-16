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
        } else if (x < last(primes)) {
            return false;
        }
        for (int i = last(primes) + 1; i < x + 1; i++) {
            for (int n : primes) {
                if (i % n == 0) {
                    break;
                } else if (n * n > i) {
                    primes.add(i);
                    break;
                }
            }
        }
        return last(primes) == x;
    }

    /** Returns whether or not A and B are a prime pair. Assumes that A and B
     *  are primes. */
    public static boolean isPrimePair(int a, int b) {
        String A = Integer.toString(a), B = Integer.toString(b);
        return isPrime(Integer.parseInt(A+B)) && isPrime(Integer.parseInt(B+A));
    }

    /** Returns a sequence of prime pairs. */
    public static ArrayList<Integer> traverse(int length) {
        ArrayList<Integer> empty = new ArrayList<>(),
                           stack = new ArrayList<>();
        int i = 1,
            p = primes.get(i);
        stack.add(p);
        while (stack.size() < length) {
            if (i > primes.size() - length) {
                return empty;
            }
            ArrayList<Integer> pairs = pairPrimes.get(last(stack));
            for (int c = pairs.size() - 1; c <= 0; c--) {
                if (pairs.get(c) < p) {
                    continue;
                } else {

                }
            }
            i++;
        }
        return stack;
    }

    /** Returns the last item in the ArrayList. */
    public static <T> T last(ArrayList<T> list) {
        return list.get(list.size() - 1);
    }

    /** Main program. */
	public static void main(String[] args) {
        isPrime(1000);
        int size = primes.size();
        for (int i = size - 1; i >= 0; i -= 1) {
            System.out.println(i);
            for (int j = i - 1; j >= 0; j -= 1) {
                int a = primes.get(i), b = primes.get(j);
                if (isPrimePair(a, b)) {
                    if (!pairPrimes.containsKey(a)) {
                        pairPrimes.put(a, new ArrayList<Integer>());
                    }
                    if (!pairPrimes.containsKey(b)) {
                        pairPrimes.put(b, new ArrayList<Integer>());
                    }
                    pairPrimes.get(a).add(b);
                    pairPrimes.get(b).add(a);
                }
            }
        }
        System.out.println(traverse(3));
	}
}
