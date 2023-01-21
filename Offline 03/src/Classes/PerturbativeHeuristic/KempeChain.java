package Classes.PerturbativeHeuristic;

import Classes.Node;

import java.util.HashSet;
import java.util.Set;

public class KempeChain {
    private final Set<Node> nodeSet1;
    private final Set<Node> nodeSet2;
    private final int time1;
    private final int time2;

    public KempeChain(Node node1, Node node2) {
        this.nodeSet1 = new HashSet<>();
        this.nodeSet2 = new HashSet<>();
        this.time1 = node1.getTimeSlot();
        this.time2 = node2.getTimeSlot();

        // run DFS on the graph from solution starting from node1
        // and take all nodes with timeSlot of time1 or time2
        // and put them in nodeSet1 or nodeSet2 respectively
        // return once we see any node with timeslot other than time1 or time2
        makeChain(node1);
    }

    private void makeChain(Node node) {
        if (node.getTimeSlot() == time1) {
            nodeSet1.add(node);
        } else if (node.getTimeSlot() == time2) {
            nodeSet2.add(node);
        } else {
            return;
        }

        for (Node neighbour : node.getNeighbours()) {
            if (!nodeSet1.contains(neighbour) && !nodeSet2.contains(neighbour)) {
                makeChain(neighbour);
            }
        }
    }

    public void interchange() {
        for (Node node : nodeSet1) {
            node.setTimeSlot(time2);
        }
        for (Node node : nodeSet2) {
            node.setTimeSlot(time1);
        }
    }

    private boolean isOneMember() {
        return nodeSet1.size() == 1 && nodeSet2.size() == 0;
    }

    public boolean isPairSwappableWith(KempeChain that) {
        if (this.equals(that)) return false;
        if (!this.isOneMember() || !that.isOneMember()) return false;
        return this.time1 == that.time2 && this.time2 == that.time1;
    }

    public void pairSwapWith(KempeChain that) {
        this.interchange();
        that.interchange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KempeChain that)) return false;

        if (time1 != that.time1) return false;
        if (time2 != that.time2) return false;
        if (!nodeSet1.equals(that.nodeSet1)) return false;
        return nodeSet2.equals(that.nodeSet2);
    }

    @Override
    public int hashCode() {
        int result = nodeSet1.hashCode();
        result = 31 * result + nodeSet2.hashCode();
        result = 31 * result + time1;
        result = 31 * result + time2;
        return result;
    }
}
