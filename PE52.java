import java.util.ArrayList;

/** Finds the integer x such that its permuted multiples share the same digits
 *  as x. So, x, 2x, 3x, 4x, 5x, and 6x all share the same digits. */

public class PE52 {

    public static ArrayList<Integer> DIGITS = new ArrayList<>();

    /** Returns true if X and Y are permutations of each other, false otherwise.
     */
    public static boolean isPermutation(int x, int y) {
        if (Integer.toString(x).length() != Integer.toString(y).length()) {
            return false;
        }
        while (x > 0) {
            DIGITS.add((Integer) x % 10);
            x /= 10;
        }
        while (y > 0) {
            Integer digit = (Integer) y % 10;
            if (!DIGITS.contains((Integer) digit)) {
                return false;
            }
            DIGITS.remove(digit);
            y /= 10;
        }
        DIGITS.clear();
        return true;
    }

    /** Returns whether or not NUM is the integer x we are searching for. */
    public static boolean isX(int num) {
        int x2 = num * 2,
            x3 = num * 3,
            x4 = x2 * 2,
            x5 = num * 5,
            x6 = x3 * 2;
        if (Integer.toString(num * 6).length() > Integer.toString(num).length()) {
            return false;
        }
        boolean ok = true;
        ok = ok && isPermutation(num, x2);
        ok = ok && isPermutation(num, x3);
        ok = ok && isPermutation(num, x4);
        ok = ok && isPermutation(num, x5);
        ok = ok && isPermutation(num, x6);
        return ok;
    }

    public static void test() {
        System.out.println(isPermutation(123, 321) == true);
        System.out.println(isPermutation(13, 321) == false);
        System.out.println(isPermutation(45, 321) == false);
        System.out.println(isPermutation(1322, 321) == false);
        System.out.println(isPermutation(145, 293) == false);
    }

    /** Main program. */
	public static void main(String[] args) {
        int x = 1;
        while (!isX(x)) {
            System.out.printf("Failed: %d\n", x);
            x++;
        }
        System.out.printf("x is %d\n", x);
	}
}
