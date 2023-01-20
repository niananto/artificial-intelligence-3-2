package Classes.ConstructiveHeuristic;

import Classes.Node;

import java.util.Set;

public interface ICH {
    public Set<Node> getAllNodes();
    public boolean hasNext();
    public Node getNext();
}
