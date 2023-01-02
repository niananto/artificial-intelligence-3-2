import java.util.Comparator;

public class ManhattanDistanceComparator implements Comparator<Board> {
    @Override
    public int compare(Board b1, Board b2) {
        int f1 = b1.getCost() + b1.getManhattanDistance();
        int f2 = b2.getCost() + b2.getManhattanDistance();
        return Integer.compare(f1, f2);
    }
}
