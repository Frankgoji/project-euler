import java.math.BigInteger;

/** Square root convergences—finds how many fractions have more digits in the
 *  numerator than the denominator*/
public class PE57 {

    /** Rational class. */
    public static class Rational {
        public BigInteger n;
        public BigInteger d;
        public Rational(BigInteger numer, BigInteger denom) {
            BigInteger div = gcd(numer, denom);
            this.n = numer.divide(div);
            this.d = denom.divide(div);
        }

        public String toString() {
            return n.toString() + "/" + d.toString();
        }

        /** Returns the result of adding int X to this rational. */
        public Rational add(int x) {
            BigInteger X = new BigInteger(Integer.toString(x));
            return new Rational(n.add(d.multiply(X)), d);
        }

        /** Returns the result of adding rational X to this rational. */
        public Rational add(Rational x) {
            BigInteger nd = d, nn = x.n;
            if (d != x.d) {
                BigInteger gcd = gcd(d, x.d);
                nd = nd.multiply(x.d.divide(gcd));
                nn = nn.multiply(d.divide(gcd));
                nn = nn.add(n.multiply(x.d).divide(gcd));
            } else {
                nn = nn.add(n);
            }
            return new Rational(nn, nd);
        }

        /** Returns the reciprocal of this rational. */
        public Rational reciprocal() {
            return new Rational(d, n);
        }

        /** Finds the gcd of A and B. */
        public static BigInteger gcd(BigInteger a, BigInteger b) {
            if (b.compareTo(new BigInteger("0")) == 0) return a;
            return gcd(b, a.mod(b));
        }
    }

    /** Main program. */
	public static void main(String[] args) {
        int num = 0;
        Rational prev = new Rational(new BigInteger("0"), new BigInteger("1"));
        for (int i = 0; i < 1e3; i++) {
            prev = prev.add(2).reciprocal();
            Rational x = prev.add(1);
            if (x.n.toString().length() > x.d.toString().length())
                num++;
        }
        System.out.println(num);
	}
}
