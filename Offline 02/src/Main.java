import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File folder = new File("data-new");
        File[] listOfFiles = folder.listFiles();

        int num = 0;
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (!file.isFile()) continue;

            int N = 0;
            int[][] START = new int[0][];
            boolean[][] rowDomains = new boolean[0][];
            boolean[][] colDomains = new boolean[0][];
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String lineOfFile;
                int lineCount = 0;

                while ((lineOfFile = br.readLine()) != null) {
                    if (lineCount == 0) {
                        N = Integer.parseInt(lineOfFile);
                        START = new int[N][N];
                        rowDomains = new boolean[N][N];
                        colDomains = new boolean[N][N];
                        for (int i=0; i<N; i++) {
                            Arrays.fill(rowDomains[i], Boolean.TRUE);
                            Arrays.fill(colDomains[i], Boolean.TRUE);
                        }
                        lineCount++;
                        continue;
                    }
                    String[] rowVals = lineOfFile.split(" ");
                    for (int i=0; i<N; i++) {
                        // input in 1 to N, storing 0 to N-1
                        int val = Integer.parseInt(rowVals[i]) - 1;
                        START[lineCount-1][i] = val;
                        if (val >= 0) {
                            rowDomains[lineCount-1][val] = false;
                            colDomains[i][val] = false;
                        }
                    }
                    lineCount++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Variable[][] variables = new Variable[N][N];
//            VAH vah = new VAH1();
            VAH vah = new VAH2();
//            VAH vah = new VAH3();
//            VAH vah = new VAH4();
//            VAH vah = new VAH5();
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    if (START[i][j] == -1) {
                        Variable v = new Variable(N, i, j, rowDomains[i], colDomains[j]);
                        vah.addAVariable(v);
                        variables[i][j] = v;
                    } else {
                        Variable v = new Variable(N, i, j, START[i][j]);
                        variables[i][j] = v;
                    }
                }
            }

//            CSP csp = new BTSolver(N, vah, variables);
            CSP csp = new FCSolver(N, vah, variables);
            System.out.println("Solving " + file.getName());
            long startTime = System.nanoTime();
            boolean solved = csp.solverStart();
            long endTime = System.nanoTime();

            Variable[][] solution = csp.solution;
            if (solved && solutionCheck(solution)) {
                System.out.println("Solved " + file.getName() + " in " + (endTime - startTime) / 1000000 + " ms");
                System.out.println("Total Nodes: " + csp.totalNodes);
                System.out.println("Backtracks: " + csp.backtracks);
                for (int i=0; i<N; i++) {
                    for (int j=0; j<N; j++) {
                        System.out.print(solution[i][j].value + 1 + "\t");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Failed to solve " + file.getName());
            }

            num++;
//            if (num >= 4) break;
        }
    }

    private static boolean solutionCheck(Variable[][] variables) {
        int N = variables.length;
        boolean[][] rowCheck = new boolean[N][N];
        boolean[][] colCheck = new boolean[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                int val = variables[i][j].value;
                if (rowCheck[i][val] || colCheck[j][val]) {
                    return false;
                }
                rowCheck[i][val] = true;
                colCheck[j][val] = true;
            }
        }
        return true;
    }
}
