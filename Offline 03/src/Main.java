import Classes.*;
import Classes.ConstructiveHeuristic.*;
import Classes.PerturbativeHeuristic.IPH;
import Classes.PerturbativeHeuristic.KempeChainInterchange;
import Classes.PerturbativeHeuristic.PairSwapOperator;
import Classes.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {
    private static void solveToronto(String crsFile, String stuFile) {
        HashMap<Integer, Node> nodes = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>(); // taking arraylist to allow duplicates
        int studentCount = 0;

        try {
            BufferedReader br1 = new BufferedReader(new FileReader(crsFile));
            BufferedReader br2 = new BufferedReader(new FileReader(stuFile));

            String line;

            // read courses
            while ((line = br1.readLine()) != null) {
                String[] parts = line.split(" ");
                int courseNo = Integer.parseInt(parts[0]);
                int noOfStudents = Integer.parseInt(parts[1]);
                Course course = new Course(courseNo, noOfStudents);
                Node node = new Node(course);
                nodes.put(courseNo, node);
            }

            // read students
            // every line is a student
            while ((line = br2.readLine()) != null && !line.isEmpty()) {
                studentCount++;
                String[] parts = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    int courseNo = Integer.parseInt(parts[i]);

                    // get the node from the HashMap
                    Node node = nodes.get(courseNo);
                    for (int j = i + 1; j < parts.length; j++) {
                        int courseNo2 = Integer.parseInt(parts[j]);
                        Node node2 = nodes.get(courseNo2);
                        Edge edge = new Edge(node, node2);
                        // add the edge to the node. duplicates are allowed here
                        edges.add(edge);

                        // safely add the edge to the nodes as duplicates will be ignored
                        node.addEdge(edge);
                        node2.addEdge(edge);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        ICH ich = new LargestDegree(nodes.values());
        ICH ich = new SaturationDegree(nodes.values());
//        ICH ich = new LargestEnrollment(nodes.values());
//        ICH ich = new RandomOrdering(nodes.values());

        // run constructive heuristic
        Solver solver = new Solver(ich, studentCount, edges, nodes.values());
        Solution solution = solver.solve();
        System.out.println("After constructive heuristic:");
        solution.printSolution();

        // run local search (perturbation)
        IPH iph = new KempeChainInterchange(solution);
        solution = iph.runPerturbation();
        System.out.println("After kempe-chain:");
        solution.printSolution();

        iph = new PairSwapOperator(solution);
        solution = iph.runPerturbation();
        System.out.println("After pair-swap:");
        solution.printSolution();
    }

    public static void main(String[] args) {
        Random.setSeed(1805093);

//        solveToronto("Toronto/car-f-92.crs", "Toronto/car-f-92.stu");
//        solveToronto("Toronto/car-s-91.crs", "Toronto/car-s-91.stu");
//        solveToronto("Toronto/kfu-s-93.crs", "Toronto/kfu-s-93.stu");
//        solveToronto("Toronto/tre-s-92.crs", "Toronto/tre-s-92.stu");
        solveToronto("Toronto/yor-f-83.crs", "Toronto/yor-f-83.stu");
    }
}