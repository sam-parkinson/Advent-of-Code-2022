import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class HillClimber {
    private Square[][] elevationMap;
    private int[] start, end;
    private ArrayDeque<Square> path;
    private int trailLength;

    public HillClimber(String address) {
        parseInput(address);
        traverseMap(start, true);
        path = findPath(end);
        resetGrid();
        traverseMap(end, false);
        trailLength = findShortestHikingTrail();
    }

    public int getPathLength() {
        return this.path.size() - 1;
    }

    public int getTrailLength() {
        return this.trailLength;
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

        makeMap(lineArr);
    }

    private void makeMap(ArrayList<String> lineArr) {
        elevationMap = new Square[lineArr.size()][lineArr.get(0).length()];

        for (int i = 0; i < elevationMap.length; i++) {
            char[] charArr = lineArr.get(i).toCharArray();

            for (int j = 0; j < charArr.length; j++) {
                switch (charArr[j]) {
                    case 'S':
                        start = new int[]{i, j};
                        elevationMap[i][j] = new Square('a', i, j);
                        break;
                    case 'E':
                        end = new int[]{i, j};
                        elevationMap[i][j] = new Square('z', i, j);
                        break;
                    default:
                        elevationMap[i][j] = new Square(charArr[j], i, j);
                        break;
                }
            }
        }
    }

    private void traverseMap(int[] start, boolean isForwards) {
        int x = start[0];
        int y = start[1];

        ArrayDeque<Square> queue = new ArrayDeque<Square>();

        queue.addLast(elevationMap[x][y]);

        while (!queue.isEmpty()) {
            Square current = queue.removeFirst();
            current.visited = true;

            x = current.x;
            y = current.y;

            if (x > 0) {
                if (attemptClimb(isForwards, x, y, x - 1, y)) {
                    queue.addLast(elevationMap[x - 1][y]);
                }
            }

            if (y > 0) {
                if (attemptClimb(isForwards, x, y, x, y - 1)) {
                    queue.addLast(elevationMap[x][y - 1]);
                }
            }

            if (x < elevationMap.length - 1) {
                if (attemptClimb(isForwards, x, y, x + 1, y)) {
                    queue.addLast(elevationMap[x + 1][y]);
                }
            }

            if (y < elevationMap[0].length - 1) {
                if (attemptClimb(isForwards, x, y, x, y + 1)) {
                    queue.addLast(elevationMap[x][y + 1]);
                }
            }
        }
    }

    private void resetGrid() {
        for (int i = 0; i < elevationMap.length; i++) {
            for (int j = 0; j < elevationMap[i].length; j++) {
                elevationMap[i][j].visited = false;
                elevationMap[i][j].previous = null;
            }
        }
    }

    private boolean attemptClimb(boolean forwards, int x1, int y1, int x2, int y2) {
        if (!elevationMap[x2][y2].visited) {
            char current = elevationMap[x1][y1].elevation;
            char next = elevationMap[x2][y2].elevation;

            boolean climbable = forwards ? next - current <= 1 : current - next <= 1;

            if (climbable) {
                elevationMap[x2][y2].visited = true;
                elevationMap[x2][y2].previous = new int[] {x1, y1};

                return true;
            }        
        }

        return false;
    }

    private int findShortestHikingTrail() {
        int min = path.size() - 1; 

        for (int i = 0; i < elevationMap.length; i++) {
            for (int j = 0; j < elevationMap[i].length; j++) {
                if (elevationMap[i][j].elevation == 'a') {
                    min = compareTrail(i, j, min);
                }
            }
        }

        return min;
    }

    private int compareTrail(int x, int y, int old) {
        int[] start = {x, y};
        ArrayDeque<Square> trail = findPath(start);

        if (trail.peek().elevation == 'z') {
            return Math.min(old, trail.size() - 1);
        }

        return old;
    }

    private ArrayDeque<Square> findPath(int[] start) {
        ArrayDeque<Square> route = new ArrayDeque<Square>();
        Square current = elevationMap[start[0]][start[1]];
        route.addFirst(current);

        while (current.previous != null) {
            current = elevationMap[current.previous[0]][current.previous[1]];
            route.addFirst(current);
        }

        return route;
    }

    private class Square {
        char elevation;
        int x, y;
        boolean visited;
        int[] previous;

        private Square(char elevation, int x, int y) {
            this.elevation = elevation;
            this.x = x;
            this.y = y;
            this.visited = false;
            this.previous = null;
        }
    }
}