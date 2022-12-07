import java.io.File;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Scanner;

public class SignalTuner {
    private char[] signalArr;
    private int firstPacketMarker, firstMessageMarker;

    public SignalTuner(String address) {
        parseInput(address);
        firstPacketMarker = findFirstMarker(4);
        firstMessageMarker = findFirstMarker(14);
    }

    public int getFirstPacketMarker() {
        return this.firstPacketMarker;
    }

    public int getFirstMessageMarker() {
        return this.firstMessageMarker;
    }

    private void parseInput(String address) {
        String str = "";

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            str = stdin.nextLine();

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        signalArr = str.toCharArray();
    }

    private int findFirstMarker(int markerSize) {
        ArrayDeque<Character> deque = new ArrayDeque<Character>();

        // put first n - 1 characters in queue
        for (int i = 0; i < markerSize - 1; i++) {
            deque.addLast(signalArr[i]);
        }

        for (int i = markerSize - 1; i < signalArr.length; i++) {
            if (deque.size() > markerSize - 1) {
                deque.removeFirst();
            }
            deque.addLast(signalArr[i]);

            Character[] items = new Character[deque.size()];
            items = deque.toArray(items);
            HashSet<Character> set = new HashSet<>();

            for (int j = 0; j < items.length; j++) {
                set.add(items[j]);
            }

            // set is n items large
            if (set.size() == markerSize) {
                return i + 1;
            }
        }
        return -1;
    }
}