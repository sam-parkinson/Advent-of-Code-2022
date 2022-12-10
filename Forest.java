import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Forest {
    private int[][] treeGrid;
    private HashSet<String> visibleTrees;

    public Forest(String address) {
        parseInput(address);
        findVisibleTrees();
    }

    public int getVisibleTreesCount() {
        System.out.println(visibleTrees.toString());
        return this.visibleTrees.size();
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
            int x = 0;
            int y = treeGrid[x][i];
            String str = x + "," + y;

            visibleTrees.add(str);
        }

        // add last row
        for (int i = 0; i < treeGrid[treeGrid.length - 1].length; i++) {
            int x = treeGrid.length - 1;
            int y = treeGrid[x][i];
            String str = x + "," + y;

            visibleTrees.add(str);
        }

        // add first column
        for (int i = 0; i < treeGrid[0].length; i++) {
            int y = 0;
            int x = treeGrid[i][y];
            
            String str = x + "," + y;

            visibleTrees.add(str);
        }

        // add last column
        for (int i = 0; i < treeGrid[0].length; i++) {
            int y = treeGrid[0].length - 1;
            int x = treeGrid[i][y];
            
            String str = x + "," + y;

            visibleTrees.add(str);
        }

        // start left, go right
        // start right, go left
        for (int i = 0; i < treeGrid.length; i++) {
            findVisibleTreesRow(i);
        }

        // start up, go down
        // start down, go up
        for (int i = 0; i < treeGrid[0].length; i++) {
            findVisibleTreesColumn(i);
        }
    }

    private void findVisibleTreesRow(int row) {
        int max = treeGrid[row][0];

        for (int i = 1; i < treeGrid[row].length - 1; i++) {
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

        for (int i = 1; i < treeGrid.length - 1; i++) {
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

}