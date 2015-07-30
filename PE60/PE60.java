package PE60;

import static PE60.PrimeGen.isPrime;

import java.util.Collections;
import java.lang.Exception;
import java.util.HashMap;
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

/** Prime pair sets. */
public class PE60 {

    public static ArrayList<BigInteger> primes;

    /** Checks if BigIntegers A and B are prime pairs. */
    public static boolean isPrimePair(BigInteger a, BigInteger b) {
        return isPrime(new BigInteger(a.toString() + b.toString())) &&
            isPrime(new BigInteger(b.toString() + a.toString()));
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
	}
}
