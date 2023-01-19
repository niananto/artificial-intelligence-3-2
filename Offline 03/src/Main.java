import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Node> nodes = new HashMap<>();

        try {
            BufferedReader br1 = new BufferedReader(new FileReader("Toronto/yor-f-83.crs"));
            BufferedReader br2 = new BufferedReader(new FileReader("Toronto/yor-f-83.stu"));

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
            while ((line = br2.readLine()) != null) {
                String[] parts = line.split(" ");
                for (int i = 0; i < parts.length; i++) {
                    int courseNo = Integer.parseInt(parts[i]);

                    // get the node from the HashMap
                    Node node = nodes.get(courseNo);
                    for (int j = i + 1; j < parts.length; j++) {
                        int courseNo2 = Integer.parseInt(parts[j]);
                        Node node2 = nodes.get(courseNo2);
                        Edge edge = new Edge(node, node2);
                        node.addEdge(edge);
                        node2.addEdge(edge);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ICH ich = new CHLargestDegree(nodes.values());

        Solver solver = new Solver(ich);
        solver.solve();
        solver.printSolution();
    }
}
