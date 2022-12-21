import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MonkeyTroop {
    private Monkey[] calmTroop, wildTroop;
    private long monkeyBusinessCalm, monkeyBusinessWild;
    private int primeMultiple;

    public MonkeyTroop(String address) {
        parseInput(address);
        performMonkeyBusiness(calmTroop, 20);
        performMonkeyBusiness(wildTroop, 10000);
        monkeyBusinessCalm = findCurrentMonkeyBusiness(calmTroop);
        monkeyBusinessWild = findCurrentMonkeyBusiness(wildTroop);
    }

    public long getMonkeyBusinessCalm() {
        return this.monkeyBusinessCalm;
    }

    public long getMonkeyBusinessWild() {
        return this.monkeyBusinessWild;
    }

    private void parseInput(String address) {
        ArrayList<String> strArr = new ArrayList<String>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);  

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                if (!str.equals("")) 
                    strArr.add(str);                    
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        assembleTroop(strArr);
    }

    private void assembleTroop(ArrayList<String> strArr) {
        calmTroop = new Monkey[strArr.size() / 6];
        wildTroop = new Monkey[strArr.size() / 6];
        for (int i = 0; i < strArr.size(); i += 6) {
            String[] subArr = new String[5];

            for (int j = 0; j < 5; j++) {
                subArr[j] = strArr.get(j + i + 1);
            }

            calmTroop[i/6] = new Monkey(subArr, true);
            wildTroop[i/6] = new Monkey(subArr, false);
        }
    }

    private void performMonkeyBusiness(Monkey[] troop, int rounds) {
        primeMultiple = 1;
        for (int i = 0; i < troop.length; i++) {
            primeMultiple *= troop[i].testDivisor;
        }

        for (int i = 0; i < rounds; i++) {
            for (int j = 0; j < troop.length; j++) {
                troop[j].examineItems(troop);
            }
        }
    }

    private long findCurrentMonkeyBusiness(Monkey[] troop) {
        int[] itemCountArr = new int[troop.length];

        for (int i = 0; i < itemCountArr.length; i++) {
            itemCountArr[i] = troop[i].itemsInspected;
        }

        Arrays.sort(itemCountArr);

        return (long) itemCountArr[itemCountArr.length - 1] * itemCountArr[itemCountArr.length - 2];
    }

    private class Monkey {
        private ArrayDeque<Long> items;
        private boolean isTroopCalm, isOpMultiply;
        private int opMagnitude, testDivisor, trueThrow, falseThrow, itemsInspected;

        private Monkey(String[] input, boolean isTroopCalm) {
            items = new ArrayDeque<Long>();

            makeItems(input[0].trim().split(", | "));
            
            String[] opArr = input[1].trim().split(" ");

            isOpMultiply = opArr[4].equals("*");
            opMagnitude = opArr[5].equals("old") ? -1 : Integer.parseInt(opArr[5]);
            
            opArr = input[2].split(" ");
            testDivisor = Integer.parseInt(opArr[opArr.length - 1]);

            trueThrow = Character.getNumericValue(input[3].charAt(input[3].length() - 1));
            falseThrow = Character.getNumericValue(input[4].charAt(input[4].length() - 1));

            itemsInspected = 0;
            this.isTroopCalm = isTroopCalm;        
        }

        private void makeItems(String[] input) {
            for (int i = 2; i < input.length; i++) {
                items.add((long) Integer.parseInt(input[i]));
            }
        }

        private void examineItems(Monkey[] troop) {
            itemsInspected += items.size();
            while (!items.isEmpty()) {
                long item = items.removeFirst();
                long x = opMagnitude == -1 ? item : (long) opMagnitude;
            
                item = isOpMultiply ? item * x : item + x;
                if (this.isTroopCalm) {
                    item = item / 3;
                }
                
                item = item % primeMultiple;
                int index = item % testDivisor == 0 ? trueThrow : falseThrow;
                
                // do something
                troop[index].items.addLast(item);
            }
        }
    }
}