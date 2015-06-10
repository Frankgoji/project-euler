import java.lang.StringBuilder;
import java.math.BigInteger;

/** Find the number of Lychrel numbers below 10,000. */
public class PE55 {

    public static BigInteger ten = new BigInteger("10");

    public static boolean isPalindrome(BigInteger n) {
        if (n.compareTo(ten) == -1) {
            return true;
        }
        String num = n.toString();
        if (num.charAt(0) != num.charAt(num.length() - 1)) {
            return false;
        }
        return num.length() == 2 ||
            isPalindrome(new BigInteger(num.substring(1, num.length() - 1)));
    }

    public static boolean isLychrel(BigInteger n) {
        StringBuilder reverse = new StringBuilder(n.toString()).reverse();
        for (int i = 0; i < 50; i++) {
            n = n.add(new BigInteger(reverse.toString()));
            if (isPalindrome(n)) {
                return false;
            }
            reverse.replace(0, reverse.length(), n.toString()).reverse();
        }
        return true;
    }

    /** Main program. */
	public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 1e4; i++) {
            BigInteger x = new BigInteger(Integer.toString(i));
            if (isLychrel(x)) {
                System.out.printf("Lychrel number: %s\n", x.toString());
                sum++;
            }
        }
        System.out.printf("The sum is %d\n", sum);
	}
}
