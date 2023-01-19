import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CHLargestDegree implements ICH {
    private final Set<Node> initialNodes;
    private final Set<Node> nodes;

    public CHLargestDegree(Collection<Node> nodes) {
        this.nodes = new HashSet<>(nodes);
        initialNodes = Set.copyOf(nodes);
    }

    @Override
    public Set<Node> getAllNodes() {
        return initialNodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    @Override
    public boolean hasNext() {
        return !nodes.isEmpty();
    }

    @Override
    public Node getNext() {
        // Largest degree: The node with the largest number of edges (conflicting examinations) is
        // scheduled first. Tie break randomly.
        int largestDegree = 0;
        ArrayList<Node> largestDegreeNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getEdges().size() > largestDegree) {
                largestDegree = node.getEdges().size();
                largestDegreeNodes.add(node);
            }
        }
        return tieBreaker(largestDegreeNodes);
    }

    @Override
    public Node tieBreaker(ArrayList<Node> nodes) {
        // return random node
        return nodes.get((int) (Math.random() * nodes.size()));
    }
}
