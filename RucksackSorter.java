import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RucksackSorter {
    ArrayList<Rucksack> rucksacks;
    int totalCommonPriority, totalBadgePriority;

    public RucksackSorter(String address) {
        rucksacks = new ArrayList<Rucksack>();
        parseInput(address);
        totalCommonPriority = findTotalCommonPriority();
        totalBadgePriority = findTotalBadgePriority();
    }

    public int getTotalCommonPriority() {
        return this.totalCommonPriority;
    }

    public int getTotalBadgePriority() {
        return this.totalBadgePriority;
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                strArr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (String str : strArr) {
            rucksacks.add(new Rucksack(str));
        }
    }

    private int findTotalCommonPriority() {
        int tcp = 0;

        for (Rucksack sack : rucksacks) {
            char item = sack.findCommonItem();

            tcp += findPriority(item);
        }

        return tcp;
    }

    private int findTotalBadgePriority() {
        int tbp = 0;

        for (int i = 0; i < rucksacks.size(); i += 3) {
            char[] sackA = rucksacks.get(i).totalSack;
            char[] sackB = rucksacks.get(i + 1).totalSack;
            char[] sackC = rucksacks.get(i + 2).totalSack;
            char badge = findCommonBadge(sackA, sackB, sackC);

            tbp += findPriority(badge);
        }

        return tbp;
    }

    private char findCommonBadge(char[] sackA, char[] sackB, char[] sackC) {
        for (int i = 0; i < sackA.length; i++) {
            if (Arrays.binarySearch(sackB, sackA[i]) >= 0) {
                if (Arrays.binarySearch(sackC, sackA[i]) >= 0) {
                    return sackA[i];
                }
            }
        }

        return ' ';
    }

    private int findPriority(char a) {
        if (Character.isLowerCase(a)) {
            return ((int) a) - 96;
        } else if (Character.isUpperCase(a)) {
            return ((int) a) - 38;
        } else {
            return 0;
        }
    }

    private class Rucksack {
        char[] totalSack;
        char[] firstCompartment;
        char[] secondCompartment;

        private Rucksack(String input) {
            totalSack = input.toCharArray();
            firstCompartment = input.substring(0, input.length()/2).toCharArray();
            secondCompartment = input.substring(input.length()/2, input.length()).toCharArray();
            Arrays.sort(totalSack);
            Arrays.sort(firstCompartment);
            Arrays.sort(secondCompartment);
        }

        private char findCommonItem() {

            for (int i = 0; i < firstCompartment.length; i++) {
                if (Arrays.binarySearch(secondCompartment, firstCompartment[i]) >= 0) {
                    return firstCompartment[i];
                }
            }

            return ' ';
        }
    }
}
