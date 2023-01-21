package Classes.PerturbativeHeuristic;

import Classes.Node;

import java.util.HashSet;
import java.util.Set;

public class KempeChain {
    private final Set<Node> nodes;
    private final int time1;
    private final int time2;

    public KempeChain(int time1, int time2) {
        this.nodes = new HashSet<>();

        // make sure time1 is always smaller to help
        // in equals, hashcode, compare swappable
        if (time1 < time2) {
            this.time1 = time1;
            this.time2 = time2;
        } else {
            this.time1 = time2;
            this.time2 = time1;
        }
    }

    public void interchange() {
        for (Node node : nodes) {
            if (node.getTimeSlot() == time1) {
                node.setTimeSlot(time2);
            } else {
                node.setTimeSlot(time1);
            }
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public boolean isOneMember() {
        return nodes.size() == 1;
    }

    public boolean isPairSwappableWith(KempeChain that) {
        return this.time1 == that.time1 && this.time2 != that.time2;
    }

    public void pairSwap(KempeChain that) {
        if (!this.isOneMember() || !that.isOneMember()) return;

        this.interchange();
        that.interchange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KempeChain that)) return false;

        if (time1 != that.time1) return false;
        if (time2 != that.time2) return false;
        return nodes.equals(that.nodes);
    }

    @Override
    public int hashCode() {
        int result = nodes.hashCode();
        result = 31 * result + time1;
        result = 31 * result + time2;
        return result;
    }
}
