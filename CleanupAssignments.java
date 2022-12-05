import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CleanupAssignments {
    ArrayList<CleanupCrew> crewList;
    int mutualOverlapCount, anyOverlapCount;

    public CleanupAssignments(String address) {
        crewList = new ArrayList<CleanupCrew>();
        parseInput(address);
        mutualOverlapCount = 0;
        anyOverlapCount = 0;
        countOverlaps();
    }

    public int getMutualOverlapCount() {
        return this.mutualOverlapCount;
    }

    public int getAnyOverlapCount() {
        return this.anyOverlapCount;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                crewList.add(new CleanupCrew(stdin.nextLine()));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void countOverlaps() {

        for (CleanupCrew crew : crewList) {
            if (crew.hasMutualOverlap())
                mutualOverlapCount++;

            if (crew.hasAnyOverlap())
                anyOverlapCount++;
        }

    }

    private class CleanupCrew {
        int startA, endA, startB, endB;

        private CleanupCrew(String input) {
            String[] strArr = input.split("[^0-9]");

            startA = Integer.parseInt(strArr[0]);
            endA = Integer.parseInt(strArr[1]);
            startB = Integer.parseInt(strArr[2]);
            endB = Integer.parseInt(strArr[3]);
        }

        private boolean hasMutualOverlap() {
            return (endA >= endB && startA <= startB) || (endA <= endB && startA >= startB);
        }

        private boolean hasAnyOverlap() {
            // conditions for any overlap
            // mutual overlap condition
            // a starts before/when b starts and ends after/when b starts
            // b starts before/when a starts and ends after/when a starts
            return (startA <= startB && endA >= startB) || (startA >= startB && startA <= endB);
        }

        
            // (startA >= endB && startA <= startB) || 
            // (endA <= endB && startA >= startB) || 

    }
}