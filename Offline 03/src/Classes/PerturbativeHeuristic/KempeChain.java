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
        this.time1 = time1;
        this.time2 = time2;
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
}
