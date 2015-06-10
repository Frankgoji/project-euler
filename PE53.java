import java.math.BigInteger;

/** Find the number of values of nCr >= 100 for 1 <= n <= 100. */
public class PE53 {

    public static BigInteger one = new BigInteger("1");
    public static BigInteger hundred = new BigInteger("100");
    public static BigInteger million = new BigInteger("1000000");

    /** Returns X! */
    public static BigInteger factorial(BigInteger x) {
        if (x.compareTo(one) < 1) {
            return one;
        }
        BigInteger n = new BigInteger("1");
        while (x.compareTo(one) == 1) {
            n = n.multiply(x);
            x = x.subtract(one);
        }
        return n;
    }

    /** Returns nCr for N and R. */
    public static BigInteger choose(BigInteger n, BigInteger r) {
        return factorial(n).divide(factorial(n.subtract(r)).multiply(factorial(r)));
    }

    /** Main program. */
	public static void main(String[] args) {
        int count = 0;

        for (BigInteger n = new BigInteger("1"); n.compareTo(hundred) < 1; n = n.add(one)) {
            for (BigInteger i = new BigInteger("0"); i.compareTo(n) < 1; i = i.add(one)) {
                if (choose(n, i).compareTo(million) == 1) {
                    count++;
                }
            }
        }
        System.out.printf("The count is %d\n", count);
	}
}
