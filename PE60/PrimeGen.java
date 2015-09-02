package PE60;

import java.math.BigInteger;
import java.util.ArrayList;
import java.lang.StringBuilder;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

// TODO: REWRITE ALL OF THIS SO THAT IT USES THE NONNAIVE APPROACH

/** Generates prime numbers in large quantity, making use of the BigInteger
 *  class, then stores all the numbers as a list of strings and stores it in a
 *  text file. */
public class PrimeGen {

    public static BigInteger ONE = new BigInteger("1");
    public static BigInteger ZERO = new BigInteger("0");
    public static BigInteger INCR = new BigInteger("10000000");

    /** The seed of primes. */
    public static ArrayList<BigInteger> primes = new ArrayList<>(Arrays.asList(
                new BigInteger("2"),
                new BigInteger("3"),
                new BigInteger("5"),
                new BigInteger("7")));

    /** Returns if N is prime or not. */
    public static boolean isPrime(BigInteger n) {
        if (primes.contains(n)) {
            return true;
        } else if (n.compareTo(last(primes)) < 0) {
            return false;
        }
        for (BigInteger i = last(primes).add(ONE); i.compareTo(n) <= 0; i =
                i.add(ONE)) {
            if (i.mod(new BigInteger("10000")).compareTo(ZERO) == 0) System.out.println(i);
            for (BigInteger x : primes) {
                if (i.mod(x).compareTo(ZERO) == 0) {
                    break;
                } else if (x.multiply(x).compareTo(i) > 0) {
                    primes.add(i);
                    break;
                }
            }
        }
        return last(primes).compareTo(n) == 0;
    }

    /** Uses Newton's Methond of Approximation to estimate a square root for N.
     */
    public static BigInteger sqrt(BigInteger n) {
        BigInteger xi = n.divide(primes.get(0));
        for (int i = 0; i < 3; i++) {
            xi = xi.subtract(xi.pow(2).subtract(n).divide(xi.multiply(new
                            BigInteger("2"))));
        }
        return (xi.pow(2).compareTo(n) > 0) ? xi : xi.add(ONE);
    }

    /** Returns the last item of LST. */
    public static <T> T last(ArrayList<T> lst) {
        return lst.get(lst.size() - 1);
    }

    /** Main program. */
	public static void main(String[] args) {
        Path primePath = FileSystems.getDefault().getPath("PE60/primes.txt");
        List<String> x = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(primePath,
                    StandardCharsets.UTF_8)) {
            x = Arrays.asList(reader.readLine().split(","));
        } catch (IOException i) {
            System.out.println("IOException");
            System.exit(1);
        }

        primes.clear();
        for (String n : x) {
            primes.add(new BigInteger(n));
        }

        isPrime(last(primes).add(INCR));
        StringBuilder allPrimes = new StringBuilder();
        for (int i = 0; i < primes.size() - 1; i++) {
            allPrimes.append(primes.get(i).toString() + ",");
        }
        allPrimes.append(last(primes).toString());

        try (BufferedWriter writer = Files.newBufferedWriter(primePath,
                    StandardCharsets.UTF_8)) {
            writer.write(allPrimes.toString(), 0, allPrimes.length());
        } catch (IOException i) {
            System.out.println("io problem");
        }
	}
}
