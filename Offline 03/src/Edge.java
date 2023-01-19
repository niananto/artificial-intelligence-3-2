import java.util.Objects;

public class Edge {
    private int weight; // penalty
    private Node node1;
    private Node node2;

    public Edge(Node node1, Node node2) {
        this.weight = -1;
        this.node1 = node1;
        this.node2 = node2;
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
        return Objects.hash(weight, node1.getCourse().getCourseNo(), node2.getCourse().getCourseNo());
    }

    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge) obj;
        return (weight == e.weight) && (node1 == e.node1) && (node2 == e.node2);
    }
}
