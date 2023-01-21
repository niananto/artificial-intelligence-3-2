package Classes.ConstructiveHeuristic;

import Classes.Node;
import Classes.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class LargestEnrollment implements ICH {
    private final Classes.ConstructiveHeuristic.CHSkeleton chSkeleton;

    public LargestEnrollment(Collection<Node> nodes) {
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
        // Largest enrollment: The largest number of students registered for the examinations is
        // scheduled first.
        int largestEnrollment = -1;
        ArrayList<Node> largestEnrollmentNodes = new ArrayList<>();
        for (Node node : chSkeleton.getNodes()) {
            if (node.getEnrollmentCount() == largestEnrollment) {
                largestEnrollment = node.getEnrollmentCount();
                largestEnrollmentNodes.add(node);
            } else if (node.getEnrollmentCount() > largestEnrollment) {
                largestEnrollment = node.getEnrollmentCount();
                largestEnrollmentNodes.clear();
                largestEnrollmentNodes.add(node);
            }
        }

        Node toBeReturned = tieBreaker(largestEnrollmentNodes);
        chSkeleton.removeNode(toBeReturned);
        return toBeReturned;
    }

    private Node tieBreaker(ArrayList<Node> nodes) {
        // tie-break randomly
        return nodes.get((int) (Random.nextDouble() * nodes.size()));
    }
}
