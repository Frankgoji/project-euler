import java.lang.StringBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/** XOR decryption. */
public class PE59 {

    /** Increments the StringBuilder SB by mutating its chars with setCharAt. */
    public static void increment(StringBuilder sb) {
        incrementHelper(sb, sb.length() - 1);
    }

    /** Helper function for increment. Makes it tail-recursive. */
    private static void incrementHelper(StringBuilder sb, int i) {
        if (i == -1) {
            sb.insert(0, 'a');
        } else if (sb.charAt(i) == 'z') {
            sb.setCharAt(i, 'a');
            incrementHelper(sb, i - 1);
        } else {
            sb.setCharAt(i, (char) (sb.charAt(i) + (char) 1));
        }
    }

    /** Binary to int. */
    public static int binToInt(int bin) {
        if (bin < 10) {
            return bin;
        }
        return 2 * binToInt(bin / 10) + bin % 10;
    }

    /** Int to binary. */
    public static int intToBin(int i) {
        if (i < 2) {
            return i;
        }
        return 10 * intToBin(i / 2) + i % 2;
    }

    /** Finds the XOR of the values ints A and B. */
    public static int xor(int a, int b) {
        int binA = intToBin(a), binB = intToBin(b);
        return binToInt(xorBinary(binA, binB));
    }

    /** Finds the XOR of the binary values A and B. */
    public static int xorBinary(int a, int b) {
        if (a < 10 && b < 10) {
            return (a == b) ? 0 : 1;
        }
        return 10 * xorBinary(a / 10, b / 10) + ((a % 10 == b % 10) ? 0 : 1);
    }

    /** Returns the string resulting from applying the XOR cipher technique with
     *  KEY on MESSAGE. It will store the decoded bytes in ORIGINAL. */
    public static String cipher(String key, ArrayList<String> message,
            ArrayList<String> original) {
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < message.size(); i++) {
            int xorRes = xor(Integer.parseInt(message.get(i)),
                    (int) key.charAt(i % 3));
            decoded.append((char) xorRes);
            original.set(i, Integer.toString(xorRes));
        }
        return decoded.toString();
    }

    /** Main program. */
	public static void main(String[] args) throws IOException {
        String key = "aaa";
        StringBuilder keyB = new StringBuilder(key);
        StringBuilder message = new StringBuilder();

        File cipherDir = new File("/media/frankgoji/Windows/Users/Frankgoji/" +
                "Documents/personal_projects/project_euler/p059_cipher.txt");
        Path cipher = cipherDir.toPath();
        String cipherMessage = Files.readAllLines(cipher,
                StandardCharsets.UTF_8).get(0);
        ArrayList<String> messageBytes =
            new ArrayList<>(Arrays.asList(cipherMessage.split(",")));

        String originalMessage = "";
        ArrayList<String> originalBytes = new ArrayList<>(messageBytes);
        while (!key.equals("zzz")) {
            originalMessage = cipher(key, messageBytes, originalBytes);
            if (originalMessage.contains("reborn")) {
                break;
            }
            increment(keyB);
            key = keyB.toString();
        }
        int sum = 0;
        for (String x : originalBytes) {
            sum += Integer.parseInt(x);
        }
        System.out.println(sum);
	}
}
