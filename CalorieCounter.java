import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CalorieCounter {
    private ArrayList<Elf> elfArr;
    private int[] greatestSums;

    public CalorieCounter(String address) {
        elfArr = new ArrayList<Elf>();
        parseInput(address);
        greatestSums = findGreatestSums();
    }

    public int getMostCalories() {
        return this.greatestSums[0];
    }

    public int getTopThreeCalories() {
        int sum = 0;
        for (int i = 0; i < greatestSums.length; i++) {
            sum += greatestSums[i];
        }
        return sum;
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while(stdin.hasNextLine()) {
                strArr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        makeElfArray(strArr);
    }

    private void makeElfArray(ArrayList<String> input) {
        int n = 0;
        ArrayList<String> inventory = new ArrayList<String>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).equals("")) {
                // construct new elf, append to array
                String[] inv = new String[inventory.size()];
                inv = inventory.toArray(inv);
                elfArr.add(new Elf(n, inv));
                
                inventory.clear();
                n++;
            } else {
                // put number in arrayList
                inventory.add(input.get(i));
            }
        }
    }

    private int[] findGreatestSums() {
        int[] maxArr = new int[3];
        for (Elf e : elfArr) {
            if (e.sum >= maxArr[0]) {
                maxArr[2] = maxArr[1];
                maxArr[1] = maxArr[0];
                maxArr[0] = e.sum;
            } else if (e.sum >= maxArr[1]) {
                maxArr[2] = maxArr[1];
                maxArr[1] = e.sum;
            } else if (e.sum > maxArr[2]) {
                maxArr[2] = e.sum;
            }
            
        }
        return maxArr;
    }

    private class Elf {
        int number;
        int sum;
        int[] foodList;

        private Elf(int number, String[] input) {
            this.number = number;
            foodList = new int[input.length];
            sum = 0;

            for (int i = 0; i < foodList.length; i++) {
                foodList[i] = Integer.parseInt(input[i]);
                sum += foodList[i];
            }
        }
    }
}

