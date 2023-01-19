import java.util.Objects;

public class Edge {
    private int weight; // penalty
    private final Node node1;
    private final Node node2;

    public Edge(Node node1, Node node2) {
        this.weight = -1;

        // make sure node1 < node2 always
        // it helps in the hashCode() and equals() methods
        // so that for different students, the same edge is not created twice
        if (node1.getCourse().getCourseNo() < node2.getCourse().getCourseNo()) {
            this.node1 = node1;
            this.node2 = node2;
        } else {
            this.node1 = node2;
            this.node2 = node1;
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", node1=" + node1 +
                ", node2=" + node2 +
                '}';
    }

    public Node getOtherNode(Node node) {
        if (node == node1) {
            return node2;
        } else if (node == node2) {
            return node1;
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1.getCourse().getCourseNo(), node2.getCourse().getCourseNo());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge e)) return false;
        return /*(weight == e.weight) &&*/ (node1 == e.node1) && (node2 == e.node2);
    }
}
