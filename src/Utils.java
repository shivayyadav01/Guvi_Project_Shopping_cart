import java.util.Base64;
import java.util.Scanner;

public class Utils {
    public static String encrypt(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decrypt(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }

    public static int readInt(Scanner sc, int min, int max) {
        int val;
        while (true) {
            try {
                String line = sc.nextLine();
                val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
                System.out.print("Enter number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}
