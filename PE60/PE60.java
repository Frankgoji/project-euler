import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.lang.Exception;
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

    // Rewrite to be recursive. I'm so done with using the stack.
    public static ArrayList<Integer> traverseRec(int length) {
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(3);
        return helper(stack, length);
    }

    public static ArrayList<Integer> helper(ArrayList<Integer> stack,
            int length) {
        ArrayList<Integer> empty = new ArrayList<>();
        if (stack.size() == length) {
            return stack;
        }
        ArrayList<Integer> newStack = new ArrayList<>(stack),
            pairs = pairPrimes.get(last(stack)),
            check = new ArrayList<>();
        Collections.reverse(pairs);
        // for loop
        for (int p : pairs) {
            // do something
            boolean fits = true;
            for (int num : stack) {
                if (!isPrimePair(num, p)) {
                    fits = false;
                }
            }
            if (fits && p > last(stack)) {
                newStack.add(p);
            } else {
                continue;
            }
            check = helper(newStack, length);
            if (check.size() == 0) {
                return empty;
            } else if (check.size() == length) {
                return check;
            }
            newStack = new ArrayList<>(stack);
        }
        return empty;
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
            int stsize = stack.size();
            for (int c = pairs.size() - 1; c >= 0; c--) {
                int t = pairs.get(c);
                if (t < p) {
                    continue;
                } else {
                    boolean fitsAll = true;
                    for (int j : stack) {
                        if (!isPrimePair(t, j)) {
                            fitsAll = false;
                            break;
                        }
                    }
                    if (fitsAll) {
                        stack.add(t);
                        i = primes.indexOf(t);
                        break;
                    }
                }
            }
            if (stsize != stack.size() - 1) {
                stack.remove(stsize - 1);
                i = increment(stack, i + 1);
            }
        }
        return stack;
    }

    /** Returns the last item in the ArrayList. */
    public static <T> T last(ArrayList<T> list) {
        return list.get(list.size() - 1);
    }

    /** Increments STACK by adding the nearest prime to I, returning I. */
    public static int increment(ArrayList<Integer> stack, int i) {
        while (i < primes.size() && !pairPrimes.containsKey(primes.get(i))) {
            i++;
        }
        if (i < primes.size()) {
            if (primes.get(i) == 769) {
                System.out.println(stack);
            }
            stack.add(primes.get(i));
        }
        return i;
    }

    /** Checks if I is a prime pair with all the other primes in LST. */
    public static boolean doesFit(int i, ArrayList<Integer> lst) {
        for (int j : lst) {
            if (!isPrimePair(i, j)) {
                return false;
            }
        }
        return true;
    }

    /** Finds the next prime that fits all the primes in LST. */
    public static int nextFit(ArrayList<Integer> lst) throws Exception {
        int i = primes.indexOf(last(lst)) + 1;
        while (!doesFit(primes.get(i), lst)) {
            i++;
            if (primes.get(i) > 200000) return -1;
            if (last(primes) < 0) throw new Exception("negative vals");
        }
        return primes.get(i);
    }

    /** Main program. */
	public static void main(String[] args) throws Exception {
        isPrime(5000);
        /*
        int size = primes.size();
        for (int i = size - 1; i >= 0; i -= 1) {
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
        ArrayList<Integer> theFive = traverseRec(5);
        */
        ArrayList<Integer> theFive = new ArrayList<>();
        int len = 5;
        theFive.add(3);
        while (theFive.size() < len) {
            int n = nextFit(theFive);
            if (n == -1) {
                n = primes.get(primes.indexOf(theFive.get(0)) + 1);
                theFive = new ArrayList<Integer>();
            }
            theFive.add(n);
            System.out.println(theFive);
        }
        System.out.println(theFive);
        int sum = 0;
        for (int k : theFive) {
            sum += k;
        }
        System.out.println(sum);
	}
}
