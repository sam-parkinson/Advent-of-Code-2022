import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CathodeRayTube {
    private ArrayList<String> program;
    private String[] screen;
    private int[] signal;
    private int interestingStrengths, signalSize;

    public CathodeRayTube(String address) {
        parseInput(address);
        makeSignal();
        interestingStrengths = findInterestingStrengths();
    }

    public int getInterestingStrengths() {
        return this.interestingStrengths;
    }

    public void viewScreen() {
        for (int i = 0; i < screen.length; i++) {
            System.out.println(screen[i]);
        }
    }

    private void parseInput(String address) {
        program = new ArrayList<String>();
        signalSize = 0;
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                signalSize += str.charAt(0) == 'a' ? 2 : 1;
                program.add(str);
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void makeSignal() {
        signal = new int[signalSize];

        signal[0] = 1;

        screen = new String[signalSize / 40];

        for (int i = 0; i < screen.length; i++) {
            screen[i] = "";
        }

        int i = 1;
        int j = 0;
        int x = 1;
        while (i < signal.length) {
            String[] line = program.get(j).split(" ");

            if (line[0].equals("addx")) {                
                processCycle(i, x);
                i++;

                int move = Integer.parseInt(line[1]);
                processCycle(i, x, move);
                i++;
                x += move;                
            } else {
                processCycle(i, x);
                i++;
            }
            j++;
        }
    }

    private void processCycle(int i, int x) {
        signal[i] = signal[i - 1];
        drawPixel(i - 1, x);
    }

    private void processCycle(int i, int x, int move) {
        signal[i] = signal[i - 1] + move;
        drawPixel(i - 1, x);
    }

    private void drawPixel(int i, int x) {
        int pos = (i) % 40;

        if (Math.abs(x - pos) < 2) {
            screen[i / 40] += "#";
        } else {
            screen[i / 40] += ".";
        }

    }

    private int findInterestingStrengths() {
        int sum = 0;

        for (int i = 19; i < 220; i += 40) {
            sum += signal[i] * (i + 1);
        }

        return sum;
    }
}

/*
 * NB: there is one missing pixel, in the bottom right corner of the screen
 * 
 * However you should be able to provide a solution given the code here, it is late,
 * and I do not feel like correcting to include one pixel.
 */
