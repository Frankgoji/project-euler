import java.math.BigInteger;

/** Find the maximum sum of the digits for numbers of the form a^b, where a and
 *  b are natural numbers less than 100. */
public class PE56 {

    /** Finds the digital sum of N. */
    public static int digitalSum(BigInteger n) {
        int sum = 0;
        String num = n.toString();
        for (int i = 0; i < num.length(); i++) {
            sum += Integer.parseInt(num.substring(i, i + 1));
        }
        return sum;
    }

    /** Main program. */
	public static void main(String[] args) {
        int maxSum = 0;
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                BigInteger A = new BigInteger(Integer.toString(a)),
                           aPOWb = A.pow(b);
                maxSum = Math.max(maxSum, digitalSum(aPOWb));
            }
        }
        System.out.println(maxSum);
	}
}
