import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        r.readLine(); // skip first value

        String n = r.readLine();
        System.out.println(n);
        long i = 0;
        while (true) {
            i++;
            if (i % 1000 == 0) {
                System.gc();
            }
            String m = r.readLine();
            if (m == null || m.length() == 0) {
                r.close();
                break;
            }
            if (m.equals(n)) {
                n = m;
                continue;
            }
            n = m;
            System.out.println(n);
        }
    }
}