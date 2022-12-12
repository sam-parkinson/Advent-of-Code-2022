import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class RopeBridge {
    private Instruction[] instructions;
    private HashSet<String> tailVisited, longTailVisited;
    private int[][] longRope;

    public RopeBridge(String address) {
        parseInput(address);
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

    private void followInstructions() {
        tailVisited.add(Arrays.toString(longRope[1]));
        longTailVisited.add(Arrays.toString(longRope[longRope.length - 1]));
        for (int i = 0; i < instructions.length; i++) {
            switch (instructions[i].direction) {
                case "L":
                    goLeft(instructions[i].distance, longRope);
                    break;
                case "R":
                    goRight(instructions[i].distance, longRope);
                    break;
                case "U":
                    goUp(instructions[i].distance, longRope);
                    break;
                case "D":
                default:
                    goDown(instructions[i].distance, longRope);
                    break;
            }
        }
    }

    private void goLeft(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][0]--;

            pullRope(rope);
        }
    }

    private void goRight(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][0]++;

            pullRope(rope);
        }
    }

    private void goUp(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][1]++;

            pullRope(rope);
        }
    }

    private void goDown(int dist, int[][] rope) {
        for (int i = 0; i < dist; i++) {
            rope[0][1]--;

            pullRope(rope);
        }
    }

    private void pullRope(int[][] rope) {
        for (int i = 0; i < rope.length - 1; i++) {    
            if (rope[i+1][0] - rope[i][0] > 1 && rope[i+1][1] == rope[i][1]) {
                // left
                rope[i+1][0]--;
            } else if (rope[i][0] - rope[i+1][0] > 1 && rope[i+1][1] == rope[i][1]) {
                // right
                rope[i+1][0]++;
            } else if (rope[i][1] - rope[i+1][1] > 1 && rope[i+1][0] == rope[i][0]) {
                // up
                rope[i+1][1]++;
            } else if (rope[i+1][1] - rope[i][1] > 1 && rope[i+1][0] == rope[i][0]) {
                // down
                rope[i+1][1]--; 
            } else if (rope[i+1][0] > rope[i][0] && rope[i+1][1] < rope[i][1]) {
                // up and left
                if (rope[i+1][0] - rope[i][0] > 1 || rope[i][1] - rope[i+1][1] > 1) {
                    rope[i+1][0]--;
                    rope[i+1][1]++;
                }
            } else if (rope[i+1][0] < rope[i][0] && rope[i+1][1] < rope[i][1]) {
                // up and right
                if (rope[i][0] - rope[i+1][0] > 1 || rope[i][1] - rope[i+1][1] > 1) {
                    rope[i+1][0]++;
                    rope[i+1][1]++;
                }    
            } else if (rope[i+1][0] > rope[i][0] && rope[i+1][1] > rope[i][1]) {
                // down and left
                if (rope[i+1][0] - rope[i][0] > 1 || rope[i+1][1] - rope[i][1] > 1) {
                    rope[i+1][0]--;
                    rope[i+1][1]--;
                }    
            } else if (rope[i+1][0] < rope[i][0] && rope[i+1][1] > rope[i][1]) {
                // down and right
                if (rope[i][0] - rope[i+1][0] > 1 || rope[i+1][1] - rope[i][1] > 1) {
                    rope[i+1][0]++;
                    rope[i+1][1]--;
                }               
            }
        }

        tailVisited.add(Arrays.toString(rope[1]));
        longTailVisited.add(Arrays.toString(rope[rope.length - 1])); 
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
