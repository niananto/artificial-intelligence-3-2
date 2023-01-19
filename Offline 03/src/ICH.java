import java.util.ArrayList;
import java.util.Set;

public interface ICH {
    public Set<Node> getAllNodes();
    public void addNode(Node node);
    public void removeNode(Node node);
    public boolean hasNext();
    public Node getNext();
    public Node tieBreaker(ArrayList<Node> nodes);
}
