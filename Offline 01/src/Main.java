import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private static boolean checkSolvability(Board board) {
        int k = board.getK();

        int[] flatMap = Stream.of(board.getMap())
                .flatMapToInt(IntStream::of)
                .toArray();
        int inversionCount = 0;
        int blankRowNo = -1;
        for (int i=0; i<k*k; i++) {
            if (flatMap[i] == -1) {
                blankRowNo = i/k;
                continue;
            }
            for (int j=i; j<k*k; j++) {
                if (flatMap[j] == -1) continue;

                if (flatMap[i] > flatMap[j]) inversionCount++;
            }
        }

        if (k%2 != 0) {
            return inversionCount%2 == 0;
        } else {
            // solvable if the blank is on an odd row counting from the bottom
            // that means even row counting from the top
            return (inversionCount%2 == 0 && blankRowNo%2 != 0) || (inversionCount%2 != 0 && blankRowNo%2 == 0);
        }
    }

    private static int mergeAndCount(int[] arr, int l, int m, int r) {
        int[] left = Arrays.copyOfRange(arr, l,m + 1);
        int[] right = Arrays.copyOfRange(arr,m + 1,r + 1);

        int i = 0, j = 0, k = l, swaps = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
                swaps += (m + 1) - (l + i);
            }
        }
        while (i < left.length)
            arr[k++] = left[i++];
        while (j < right.length)
            arr[k++] = right[j++];
        return swaps;
    }

    private static int mergeSortAndCount(int[] arr, int l, int r) {
        int count = 0;

        if (l < r) {
            int m = (l + r) / 2;

            // Total inversion count = left
            // subarray count + right subarray
            // count + merge count

            // Left subarray count
            count += mergeSortAndCount(arr, l, m);

            // Right subarray count
            count += mergeSortAndCount(arr, m + 1, r);

            // Merge count
            count += mergeAndCount(arr, l, m, r);
        }

        return count;
    }

    private static boolean checkSolvabilityEfficient(Board board) {
        int k = board.getK();

        int[] flatMap = Stream.of(board.getMap())
                .flatMapToInt(IntStream::of)
                .toArray();

        int inversionCount = mergeSortAndCount(flatMap, 0, flatMap.length-1);
        int blankRowNo = board.getBlankI();

        if (k%2 != 0) {
            return inversionCount%2 == 0;
        } else {
            // solvable if the blank is on an odd row counting from the bottom
            // that means even row counting from the top
            return (inversionCount%2 == 0 && blankRowNo%2 != 0) || (inversionCount%2 != 0 && blankRowNo%2 == 0);
        }
    }

    private static void solveNPuzzle(Board initialBoard, Comparator<Board> comparator, String heuristic) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(heuristic+" Output.txt"));

            PriorityQueue<Board> pq = new PriorityQueue<>(new HammingDistanceComparator());
//            ArrayList<Board> visited = new ArrayList<>();
            HashSet<Board> visited = new HashSet<>();
            pq.add(initialBoard);
            Board currentBoard = initialBoard; // assigning just so that it won't be null
            int stateCount = 1;
            int expandedCount = 0;
            int exploredCount = 0;
            while (!pq.isEmpty()) {
                currentBoard = pq.poll();
                exploredCount++;
                currentBoard.setStateNo(stateCount++);
                if (currentBoard.isGoalBoard()) {
                    System.out.println(heuristic + " : solved in " + currentBoard.getCost() + " moves");
                    bw.write(heuristic + " : solved in " + currentBoard.getCost() + " moves\n");
                    System.out.println("Expanded node: " + expandedCount + "\nExplored node: " +exploredCount);
                    bw.write("Expanded node: " + expandedCount + "\nExplored node: " +exploredCount + "\n");
                    break;
                }
                visited.add(currentBoard);
                for (Board b : currentBoard.getNeighbours()) {
                    if (!visited.contains(b)) {
                        pq.add(b);
                        expandedCount++;
                    }
                }
            }

            printSolution(currentBoard, bw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printSolution(Board currentBoard, BufferedWriter bw) {
        if (currentBoard == null) {
            return;
        }
        printSolution(currentBoard.getPreviousBoard(), bw);
        try {
            bw.write("State: " + currentBoard.getStateNo() + "\n");
            bw.write("Cost: " + currentBoard.getCost() + " Hamming: " + currentBoard.getHammingDistance() + " Manhattan: " + currentBoard.getManhattanDistance() + "\n");
            bw.write(String.valueOf(currentBoard));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt(); // number of rows and columns
        int[][] map = new int[k][k];
        for (int i=0; i<k; i++) {
            for (int j=0; j<k; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] == 0) map[i][j] = -1;
            }
        }
//        int[][] map= new int[k][k];
//        for (int i = 0; i < k; i++) {
//            for (int j = 0; j < k; j++) {
//                String str = sc.next();
//                if(str.contains("*"))
//                    map[i][j]=0;
//                else
//                    map[i][j]=Integer.parseInt(str);
//            }
//        }
        Board initialBoard = new Board(map, null);

        if (!checkSolvability(initialBoard)) {
//        if (!checkSolvabilityEfficient(initialBoard)) {
            System.out.println("The Initial Board is unsolvable");
            return;
        }

        System.out.println("Solvable. Let's solve it...\n");

        // Hamming Distance
        solveNPuzzle(initialBoard, new HammingDistanceComparator(), "Hamming");

        // Manhattan Distance
        solveNPuzzle(initialBoard, new ManhattanDistanceComparator(), "Manhattan");
    }
}
