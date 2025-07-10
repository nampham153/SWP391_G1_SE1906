import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class LogRedirect {
    public static void redirectConsoleToFile() {
        try {
            // Mở file log.txt, chế độ append
            PrintStream fileOut = new PrintStream(new FileOutputStream("log.txt", true));
            System.setOut(fileOut);   // Redirect System.out
            System.setErr(fileOut);   // Redirect System.err (tuỳ chọn)
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
