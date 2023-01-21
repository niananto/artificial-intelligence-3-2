package Classes.ConstructiveHeuristic;

import Classes.Node;
import Classes.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SaturationDegree implements ICH {
    private final CHSkeleton chSkeleton;

    public SaturationDegree(Collection<Node> nodes) {
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
        // The well known Brelaz heuristic (used in DSatur algorithm ) provides
        // dynamic variable (or node) ordering.
        int largestSaturationDegree = -1;
        ArrayList<Node> largestSaturationDegreeNodes = new ArrayList<>();
        for (Node node : chSkeleton.getNodes()) {
            if (node.getSaturationDegree() == largestSaturationDegree) {
                largestSaturationDegree = node.getSaturationDegree();
                largestSaturationDegreeNodes.add(node);
            } else if (node.getSaturationDegree() > largestSaturationDegree) {
                largestSaturationDegree = node.getSaturationDegree();
                largestSaturationDegreeNodes.clear();
                largestSaturationDegreeNodes.add(node);
            }
        }

        Node toBeReturned = tieBreaker(largestSaturationDegreeNodes);
        chSkeleton.removeNode(toBeReturned);

        // update saturation degree of neighbours
        if (toBeReturned.hasNeighbour()) {
            for (Node neighbour : toBeReturned.getNeighbours()) {
                neighbour.increaseSaturationDegree();
            }
        }

        return toBeReturned;
    }

    private Node tieBreaker(ArrayList<Node> nodes) {
        // In cases of ties, choose the vertex among these with the largest degree
        // in the subgraph induced by the uncoloured vertices
        int largestDegree = -1;
        ArrayList<Node> largestDegreeNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.hasTimeSlot()) continue;

            if (node.getEdges().size() == largestDegree) {
                largestDegree = node.getEdges().size();
                largestDegreeNodes.add(node);
            } else if (node.getEdges().size() > largestDegree) {
                largestDegree = node.getEdges().size();
                largestDegreeNodes.clear();
                largestDegreeNodes.add(node);
            }
        }

        if (largestDegreeNodes.size() == 1) {
            return largestDegreeNodes.get(0);
        } else {
            // further tie-break randomly
            return largestDegreeNodes.get((int) (Random.nextDouble() * largestDegreeNodes.size()));
        }
    }
}
