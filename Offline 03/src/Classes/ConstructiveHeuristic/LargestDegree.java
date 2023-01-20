package Classes.ConstructiveHeuristic;

import Classes.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class LargestDegree implements ICH {
    private final CHSkeleton chSkeleton;

    public LargestDegree(Collection<Node> nodes) {
        chSkeleton = new CHSkeleton(nodes);
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
        // Largest degree: The node with the largest number of edges (conflicting examinations) is
        // scheduled first.
        int largestDegree = -1;
        ArrayList<Node> largestDegreeNodes = new ArrayList<>();
        for (Node node : chSkeleton.getNodes()) {
            if (node.getEdges().size() == largestDegree) {
                largestDegree = node.getEdges().size();
                largestDegreeNodes.add(node);
            } else if (node.getEdges().size() > largestDegree) {
                largestDegree = node.getEdges().size();
                largestDegreeNodes.clear();
                largestDegreeNodes.add(node);
            }
        }
        Node toBeReturned = tieBreaker(largestDegreeNodes);
        chSkeleton.removeNode(toBeReturned);
        return toBeReturned;
    }

    private Node tieBreaker(ArrayList<Node> nodes) {
        // tie-break randomly
        return nodes.get((int) (Math.random() * nodes.size()));
    }
}
