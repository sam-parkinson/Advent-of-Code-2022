import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class RockPaperScissors {
    ArrayList<char[]> strategyGuide;
    int helpfulScore, properScore;

    public RockPaperScissors(String address) {
        strategyGuide = new ArrayList<char[]>();
        helpfulScore = 0;
        properScore = 0;
        parseInput(address);
        scoreStrategyGuide();
    }

    public int getHelpfulScore() {
        return this.helpfulScore;
    }

    public int getProperScore() {
        return this.properScore;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                strategyGuide.add(stdin.nextLine().replaceAll("\\s", "").toCharArray());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void scoreStrategyGuide() {
        for (int i = 0; i < strategyGuide.size(); i++) {
            char[] currentLine = strategyGuide.get(i);

            int opponent = (int) currentLine[0] - 64;
            int player = (int) currentLine[1] - 87;

            helpfulScore += findHelpfulScore(opponent, player);
            properScore += findProperScore(opponent, player);
        }
    }

    private int findHelpfulScore(int a, int b) {
        int score = 0;
        int result = b - a;

        score += b;

        switch (result) {
            case 0:
                score += 3;
                break;
            case 1:
            case -2:
                score += 6;
                break;
            case -1:
            case 2:
            default:
                break;
        }

        return score;
    }

    private int findProperScore(int a, int b) {
        int score = 0;
        int[] rps = {1, 2, 3};

        // (1, 2, 3) => (0, 3, 6)
        score += (b * 3) - 3;

        // array is 0-indexed, values of a are 1
        // so we only add by 1/0 to move up 2/1
        switch (b) {
            // lose - 1 back == 2 ahead
            case 1:
                score += rps[(a + 1) % 3];
                break;
            // draw - same
            case 2:
                score += a;
                break;
            // win - 1 ahead == 2 back
            case 3:
            default:
                score += rps[(a) % 3];
                break;
        }

        return score;
    }

    /*
     * Score guide
     * 
     * Lose -- 0
     * Draw -- 3
     * Win  -- 6
     * 
     * in initial round for x y z
     * A X Rock         1 1
     * B Y Paper        2 2
     * C Z Scissors     3 3
     */
}