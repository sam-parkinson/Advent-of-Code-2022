import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class RopeBridge {
    private Instruction[] instructions;
    private HashSet<String> tailVisited, longTailVisited;
    private int[][] shortRope, longRope;

    public RopeBridge(String address) {
        parseInput(address);
        shortRope = new int[2][2];
        longRope = new int[10][2];
        tailVisited = new HashSet<String>();
        longTailVisited = new HashSet<String>();

        followInstructions();
    }

    public int getTailVisitedCount() {
        return tailVisited.size();
    }

    public int getLongTailVisitedCount() {
        return longTailVisited.size();
    }

    private void parseInput(String address) {
        ArrayList<String> lineArr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                lineArr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        makeInstructions(lineArr);
    }

    private void makeInstructions(ArrayList<String> arr) {
        instructions = new Instruction[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            instructions[i] = new Instruction(arr.get(i));
        }
    }

    /*
     * The rope is not moving correctly for diagonal movements
     * 
     * This currently only works for a rope of length 2 where the tail is always going
     * to be moving predictably based on the head. I need to compare the pulling rope's
     * position with the pulled rope to find where the pulled rope moves.
     * 
     * Perhaps this means rethinking how the entire rope movement setup works. 
     */
    private void followInstructions() {
        tailVisited.add(Arrays.toString(shortRope[shortRope.length - 1]));
        longTailVisited.add(Arrays.toString(longRope[longRope.length - 1]));
        for (int i = 0; i < instructions.length; i++) {
            switch (instructions[i].direction) {
                case "L":
                    goLeft(instructions[i].distance, shortRope);
                    goLeft(instructions[i].distance, longRope);
                    break;
                case "R":
                    goRight(instructions[i].distance, shortRope);
                    goRight(instructions[i].distance, longRope);
                    break;
                case "U":
                    goUp(instructions[i].distance, shortRope);
                    goUp(instructions[i].distance, longRope);
                    break;
                case "D":
                default:
                    goDown(instructions[i].distance, shortRope);
                    goDown(instructions[i].distance, longRope);
                    break;
            }
            
        }
    }

    private void goLeft(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][0]--;

            for (int j = 0; j < rope.length - 1; j++) {
                if (rope[j+1][0] - rope[j][0] > 1) {
                    rope[j+1][0]--;
                    rope[j+1][1] = rope[j][1];
                }
    
            }
            
            HashSet<String> set = rope.length == 2 ? tailVisited : longTailVisited;
            set.add(Arrays.toString(rope[rope.length - 1]));    
        }
    }

    private void goRight(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][0]++;

            for (int j = 0; j < rope.length - 1; j++) {
                if (rope[j][0] - rope[j+1][0] > 1) {
                    rope[j+1][0]++;
                    rope[j+1][1] = rope[j][1];
                }
            }

            HashSet<String> set = rope.length == 2 ? tailVisited : longTailVisited;
            set.add(Arrays.toString(rope[rope.length - 1])); 
        }
    }

    private void goUp(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][1]++;

            for (int j = 0; j < rope.length - 1; j++) {
                if (rope[j][1] - rope[j+1][1] > 1) {
                    rope[j+1][1]++;
                    rope[j+1][0] = rope[j][0];
                }
            }

            HashSet<String> set = rope.length == 2 ? tailVisited : longTailVisited;
            set.add(Arrays.toString(rope[rope.length - 1])); 
        }
    }

    private void goDown(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][1]--;

            for (int j = 0; j < rope.length - 1; j++) {
                if (rope[j+1][1] - rope[j][1] > 1) {
                    rope[j+1][1]--; 
                    rope[j+1][0] = rope[j][0];
                }
            }
            
            HashSet<String> set = rope.length == 2 ? tailVisited : longTailVisited;
            set.add(Arrays.toString(rope[rope.length - 1])); 
        }
    }

    private class Instruction {
        private String direction;
        private int distance;

        private Instruction(String str) {
            String[] split = str.split(" ");
            direction = split[0];
            distance = Integer.parseInt(split[1]);
        }

        public String toString() {
            return (direction + ": " + distance);
        }
    }
}