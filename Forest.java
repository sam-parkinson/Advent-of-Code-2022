import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Forest {
    private int[][] treeGrid;
    private HashSet<String> visibleTrees;
    private int maxVisibilityScore;

    public Forest(String address) {
        parseInput(address);
        findVisibleTrees();
        maxVisibilityScore = findMaxVisibilityScore();
    }

    public int getVisibleTreesCount() {
        return this.visibleTrees.size();
    }

    public int getMaxVisibilityScore() {
        return this.maxVisibilityScore;
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
        
        makeTreeGrid(lineArr);
    }

    private void makeTreeGrid(ArrayList<String> lineArr) {
        treeGrid = new int[lineArr.size()][lineArr.get(0).length()];

        for (int i = 0; i < lineArr.size(); i++) {
            String[] strArr = lineArr.get(i).split("");

            for (int j = 0; j < strArr.length; j++) {
                treeGrid[i][j] = Integer.parseInt(strArr[j]);
            }
        }
    }

    private void findVisibleTrees() {
        visibleTrees = new HashSet<String>();

        // add first row
        for (int i = 0; i < treeGrid[0].length; i++) {
            String str = 0 + "," + i;
            visibleTrees.add(str);
        }

        // add last row
        for (int i = 0; i < treeGrid[treeGrid.length - 1].length; i++) {
            String str = (treeGrid.length - 1) + "," + i;
            visibleTrees.add(str);
        }

        // add first column
        for (int i = 0; i < treeGrid[0].length; i++) {
            String str = i + "," + 0;
            visibleTrees.add(str);
        }

        // add last column
        for (int i = 0; i < treeGrid[0].length; i++) {
            String str = i + "," + (treeGrid[0].length - 1);
            visibleTrees.add(str);
        }

        for (int i = 0; i < treeGrid.length; i++) {
            findVisibleTreesRow(i);
        }

        for (int i = 0; i < treeGrid[0].length; i++) {
            findVisibleTreesColumn(i);
        }
    }

    private void findVisibleTreesRow(int row) {
        int max = treeGrid[row][0];

        for (int i = 1; i < treeGrid[row].length; i++) {
            if (treeGrid[row][i] > max) {
                max = treeGrid[row][i];
                visibleTrees.add(row + "," + i);
            }
        }

        int peak = max;
        max = treeGrid[row][treeGrid[row].length - 1];

        int i = treeGrid[row].length - 2;
        while (max < peak) {
            if (treeGrid[row][i] > max) {
                max = treeGrid[row][i];
                visibleTrees.add(row + "," + i);
            }
            i--;
        }
    }

    private void findVisibleTreesColumn(int column) {
        int max = treeGrid[0][column];

        for (int i = 1; i < treeGrid.length; i++) {
            if (treeGrid[i][column] > max) {
                max = treeGrid[i][column];
                visibleTrees.add(i + "," + column);
            }
        }

        int peak = max;
        max = treeGrid[treeGrid.length - 1][column];

        int i = treeGrid.length - 2;
        while (max < peak) {
            if (treeGrid[i][column] > max) {
                max = treeGrid[i][column];
                visibleTrees.add(i + "," + column);
            }
            i--;
        }
    }

    private int findMaxVisibilityScore() {
        int max = 0;

        for (int i = 1; i < (treeGrid.length - 1); i++) {
            for (int j = 1; j < (treeGrid[i].length - 1); j++) {
                int e = findEastScore(i, j);
                int w = findWestScore(i, j);
                int n = findNorthScore(i, j);
                int s = findSouthScore(i, j);

                if (e * w * n * s > max)
                    max = e * w * n * s;
            }
        }

        return max;
    }

    private int findEastScore(int x, int y) {
        int score = 0;
        int height = treeGrid[x][y];
        boolean blocked = false;
        while (!blocked && x < treeGrid.length - 1) {        
            x++;
            score++; 
            if (treeGrid[x][y] >= height) {
                blocked = true;
            }
        }

        return score;
    }

    private int findWestScore(int x, int y) {
        int score = 0;
        int height = treeGrid[x][y];
        boolean blocked = false;
        while (!blocked && x > 0) {        
            x--;
            score++;
            if (treeGrid[x][y] >= height) {
                blocked = true;
            }       
        }

        return score;
    }

    private int findNorthScore(int x, int y) {
        int score = 0;
        int height = treeGrid[x][y];
        boolean blocked = false;
        while (!blocked && y > 0) {        
            y--;
            if (treeGrid[x][y] >= height) {
                blocked = true;
            }
            score++;    
        }

        return score;
    }

    private int findSouthScore(int x, int y) {
        int score = 0;
        int height = treeGrid[x][y];
        boolean blocked = false;
        while (!blocked && y < treeGrid[x].length - 1) {        
            y++;
            if (treeGrid[x][y] >= height) {
                blocked = true;
            }
            score++;    
        }

        return score;
    }
}