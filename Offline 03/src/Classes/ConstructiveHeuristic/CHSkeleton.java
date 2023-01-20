package Classes.ConstructiveHeuristic;

import Classes.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CHSkeleton {
    private final Set<Node> initialNodes;
    private final Set<Node> nodes;

    public CHSkeleton(Collection<Node> nodes) {
        this.nodes = new HashSet<>(nodes);
        initialNodes = Set.copyOf(nodes);
    }

    public Set<Node> getAllNodes() {
        return initialNodes;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public boolean hasNext() {
        return !nodes.isEmpty();
    }
}
