package Classes;

import java.util.Objects;

public class Edge {
    private final Node node1;
    private final Node node2;

    public Edge(Node node1, Node node2) {
        // make sure node1 < node2 always
        // it helps in the hashCode() methods
        // so that for different students, the same edge is not created twice
        if (node1.getCourse().getCourseNo() < node2.getCourse().getCourseNo()) {
            this.node1 = node1;
            this.node2 = node2;
        } else {
            this.node1 = node2;
            this.node2 = node1;
        }
    }

    public boolean maintainsHardConstraint() {
        return node1.getTimeSlot() != node2.getTimeSlot();
    }

    public int calculatePenalty() {
        int penalty = -1;
        if (node1.hasTimeSlot() && node2.hasTimeSlot()) {
            int n = Math.abs(node1.getTimeSlot() - node2.getTimeSlot());
            if (n <= 5) {
                // Exponential penalty
//                penalty = (int) Math.pow(2, 5 - n);
                // Linear penalty
                penalty = 2 * (5 - n);
            } else {
                penalty = 0;
            }
        }

        return penalty;
    }

    @Override
    public String toString() {
        return "Classes.Edge{" +
                "penalty=" + calculatePenalty() +
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
