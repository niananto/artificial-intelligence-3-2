import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File folder = new File("data-new");
        File[] listOfFiles = folder.listFiles();

        int num = 0;
        assert listOfFiles != null;
        for (File readFile : listOfFiles) {
            if (!readFile.isFile()) continue;

            int N = 0;
            int[][] START = new int[0][];
            boolean[][] rowDomains = new boolean[0][];
            boolean[][] colDomains = new boolean[0][];
            int[] rowForwardDegree = new int[0];
            int[] colForwardDegree = new int[0];
            try {
                BufferedReader br = new BufferedReader(new FileReader(readFile));
                String lineOfFile;
                int lineCount = 0;

                while ((lineOfFile = br.readLine()) != null) {
                    if (lineCount == 0) {
                        N = Integer.parseInt(lineOfFile);
                        START = new int[N][N];
                        rowDomains = new boolean[N][N];
                        colDomains = new boolean[N][N];
                        rowForwardDegree = new int[N];
                        colForwardDegree = new int[N]; // initialized to 0 by default
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
                        } else {
                            rowForwardDegree[lineCount-1]++;
                            colForwardDegree[i]++;
                        }
                    }
                    lineCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Variable[][] variables = new Variable[N][N];
//            VAH vah = new VAH1();
//            VAH vah = new VAH2();
//            VAH vah = new VAH3();
//            VAH vah = new VAH4();
            VAH vah = new VAH5();
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    if (START[i][j] == -1) {
                        Variable v = new Variable(N, i, j, rowDomains[i], colDomains[j],
                                rowForwardDegree[i]+colForwardDegree[j]+1);
                                // +1 for the so that it doesn't get zero
                                // it is eq to 2N+1 if the all the row and col are empty
                        vah.addAVariable(v);
                        variables[i][j] = v;
                    } else {
                        Variable v = new Variable(N, i, j, START[i][j]);
                        variables[i][j] = v;
                    }
                }
            }

            CSP csp = new BTSolver(N, vah, variables);
//            CSP csp = new FCSolver(N, vah, variables);
            System.out.println("Solving " + readFile.getName());
            long startTime = System.nanoTime();
            boolean solved = csp.solverStart();
            long endTime = System.nanoTime();

            // output buffered writer
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(
                        "output/" + readFile.getName() + "_" + csp.getClass().getName() + "_" + vah.getClass().getName() + ".txt", true));
                // print system time in hour and minute and second in bw
                bw.write("System time: " + new java.util.Date() + "\n");

                Variable[][] solution = csp.solution;
                if (solved && solutionCheck(solution)) {
//                    System.out.println("Solved " + readFile.getName() + " in " + (endTime - startTime) / 1000000 + " ms");
                    bw.write("Solved " + readFile.getName() + " in " + (endTime - startTime) / 1000000 + " ms\n");
//                    System.out.println("Total Nodes: " + csp.totalNodes);
                    bw.write("Total Nodes: " + csp.totalNodes + "\n");
//                    System.out.println("Backtracks: " + csp.backtracks);
                    bw.write("Backtracks: " + csp.backtracks + "\n");
                    for (int i=0; i<N; i++) {
                        for (int j=0; j<N; j++) {
                            System.out.print(solution[i][j].value + 1 + "\t");
                            bw.write(solution[i][j].value + 1 + "\t");
                        }
                        System.out.println();
                        bw.write("\n");
                    }
                } else {
                    System.out.println("Failed to solve " + readFile.getName());
                    bw.write("Failed to solve " + readFile.getName() + "\n");
                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
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
