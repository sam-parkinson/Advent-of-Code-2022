import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrateStacker {
    private ArrayList<ArrayDeque<Character>> crateStacks9000;
    private ArrayList<ArrayDeque<Character>> crateStacks9001;
    private int[][] instructions;
    private String topCrates9000, topCrates9001;

    public CrateStacker(String address) {
        parseInput(address);
        followInstructions();
        topCrates9000 = "";
        topCrates9001 = "";
        findTopCrates();
    }

    public String getTopCrates9000() {
        return this.topCrates9000;
    }

    public String getTopCrates9001() {
        return this.topCrates9001;
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

        // make crate stacks from first 8 rows
        makeCrateStacks(strArr.subList(0, 8));
        // from row 11 make instructions
        makeInstructions(strArr.subList(10, strArr.size()));
    }

    private void makeCrateStacks(List<String> lines) {
        crateStacks9000 = new ArrayList<ArrayDeque<Character>>(9);
        crateStacks9001 = new ArrayList<ArrayDeque<Character>>(9);

        for (int i = 0; i < 9; i++) {
            crateStacks9000.add(new ArrayDeque<Character>());
            crateStacks9001.add(new ArrayDeque<Character>());
        }

        // use Deque's queue function here
        // because I don't feel like counting backwards
        for (int i = 0; i < 8; i++) {
            String[] line = lines.get(i).split("");

            for (int j = 0; j < 9; j++) {
                String str = line[(j * 4) + 1];

                char[] c = str.toCharArray();
                
                if (c[0] != ' ') {
                    crateStacks9000.get(j).add(c[0]);
                    crateStacks9001.get(j).add(c[0]);
                }
            }
        }
    }

    private void makeInstructions(List<String> lines) {
        instructions = new int[lines.size()][3];

        for (int i = 0; i < lines.size(); i++) {
            String[] arr = lines.get(i).split(" ");

            instructions[i][0] = Integer.parseInt(arr[1]);
            // the input data is 1-indexed, converting it to 0-index
            instructions[i][1] = Integer.parseInt(arr[3]) - 1;
            instructions[i][2] = Integer.parseInt(arr[5]) - 1;
        }
    }

    private void followInstructions() {
        for (int i = 0; i < instructions.length; i++) {
            int[] line = instructions[i];
            followInstruction(line[0], line[1], line[2]);
        }
    }
    
    private void followInstruction(int amount, int start, int end) {
        ArrayDeque<Character> miniStack = new ArrayDeque<Character>();

        for (int i = 0; i < amount; i++) {
            crateStacks9000.get(end).push(crateStacks9000.get(start).pop());
            // System.out.println(crateStacks9001.get(start).peek());
            miniStack.push(crateStacks9001.get(start).pop());
        }

        for (int i = 0; i < amount; i++) {
            crateStacks9001.get(end).push(miniStack.pop());
        }
    }

    private void findTopCrates() {
        for (ArrayDeque<Character> crate : crateStacks9000) {
            topCrates9000 = topCrates9000 + crate.peek();
        }

        for (ArrayDeque<Character> crate : crateStacks9001) {
            topCrates9001 = topCrates9001 + crate.peek();
        }
    }
}