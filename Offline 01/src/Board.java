import java.util.*;

public class Board {
    private final int k; // number of rows and columns
    private final int[][] map;
    private final Board previousBoard;
    private final int cost;
    private int blankI;
    private int blankJ;
    private final int hammingDistance;
    private final int manhattanDistance;
    private int stateNo;

    public Board(int[][] map, Board previousBoard) {
        this.k = map.length;
        this.map = map;
        this.previousBoard = previousBoard;
        this.cost = previousBoard==null ? 0 : previousBoard.cost+1;
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                if (map[i][j] == -1) {
                    this.blankI = i;
                    this.blankJ = j;
                }
            }
        }
        this.hammingDistance = calculateHammingDistance();
        this.manhattanDistance = calculateManhattanDistance();
        this.stateNo = -1;
    }

    public int getK() {
        return k;
    }

    public int[][] getMap() {
        return map;
    }

    public Board getPreviousBoard() {
        return previousBoard;
    }

    public int getCost() {
        return cost;
    }

    public int getStateNo() {
        return stateNo;
    }

    public void setStateNo(int stateNo) {
        this.stateNo = stateNo;
    }

    public int getBlankI() {
        return blankI;
    }

    public int getHammingDistance() {
        return hammingDistance;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public boolean isGoalBoard() {
        return Arrays.deepEquals(this.map, getGoalBoard(this.k).getMap());
    }

    public static Board getGoalBoard(int k) {
        int[][] map = new int[k][k];
        int count = 1;
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                map[i][j] = count++;
            }
        }
        map[k-1][k-1] = -1;
        return new Board(map, null);
    }

    private int calculateHammingDistance() {
        int hammingDistance = 0;
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                if (map[i][j] != -1 && map[i][j] != i*k+j+1) hammingDistance++;
            }
        }
        return hammingDistance;
    }

    private int calculateManhattanDistance() {
        int goalI, goalJ;
        int manhattanDistance = 0;
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                if (map[i][j] == -1) continue;
                goalI = (map[i][j]-1)/k;
                goalJ = (map[i][j]-1)%k;
                manhattanDistance += Math.abs(goalI - i) + Math.abs(goalJ - j);
            }
        }
        return manhattanDistance;
    }

    private int[][] getNewMap(int i2, int j2, int[][] oldMap) {
        int[][] newMap = new int[k][k];
        for (int i=0; i<k; i++) {
            System.arraycopy(oldMap[i], 0, newMap[i], 0, k);
        }
        int temp = newMap[blankI][blankJ];
        newMap[blankI][blankJ] = newMap[i2][j2];
        newMap[i2][j2] = temp;

        return newMap;
    }

    public List<Board> getNeighbours() {
        List<Board> neighbours = new ArrayList<>();
        if (blankI+1 < k) {
            int[][] newMap1 = getNewMap(blankI+1, blankJ, map);
            if (previousBoard == null || !Arrays.deepEquals(previousBoard.getMap(), newMap1)) {
                neighbours.add(new Board(newMap1, this));
            }
        }
        if (blankI-1 >= 0) {
            int[][] newMap2 = getNewMap(blankI-1, blankJ, map);
            if (previousBoard == null || !Arrays.deepEquals(previousBoard.getMap(), newMap2)) {
                neighbours.add(new Board(newMap2, this));
            }
        }
        if (blankJ+1 < k) {
            int[][] newMap3 = getNewMap(blankI, blankJ+1, map);
            if (previousBoard == null || !Arrays.deepEquals(previousBoard.getMap(), newMap3)) {
                neighbours.add(new Board(newMap3, this));
            }
        }
        if (blankJ-1 >= 0) {
            int[][] newMap4 = getNewMap(blankI, blankJ-1, map);
            if (previousBoard == null || !Arrays.deepEquals(previousBoard.getMap(), newMap4)) {
                neighbours.add(new Board(newMap4, this));
            }
        }

        return neighbours;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<this.k; i++) {
            for (int j=0; j<this.k; j++) {
                str.append(map[i][j] == -1 ? "*" : map[i][j]).append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board)
            return Arrays.deepEquals(map, ((Board) obj).getMap());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
