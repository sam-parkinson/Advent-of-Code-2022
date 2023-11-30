import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DistressSignal {


    public DistressSignal(String address) {
        parseInput(address);
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {

            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}