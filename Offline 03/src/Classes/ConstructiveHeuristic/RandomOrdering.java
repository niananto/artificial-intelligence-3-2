package Classes.ConstructiveHeuristic;

import Classes.Node;
import Classes.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class RandomOrdering implements Classes.ConstructiveHeuristic.ICH {
    private final Classes.ConstructiveHeuristic.CHSkeleton chSkeleton;

    public RandomOrdering(Collection<Node> nodes) {
        chSkeleton = new Classes.ConstructiveHeuristic.CHSkeleton(nodes);
    }

    @Override
    public Set<Node> getAllNodes() {
        return chSkeleton.getAllNodes();
    }

    @Override
    public boolean hasNext() {
        return chSkeleton.hasNext();
    }

    @Override
    public Node getNext() {
        // get a random node from chSkeleton
        ArrayList<Node> nodes = new ArrayList<>(chSkeleton.getNodes());
        Node toBeReturned = nodes.get((int) (Random.nextDouble() * nodes.size()));
        chSkeleton.removeNode(toBeReturned);
        return toBeReturned;
    }
}
