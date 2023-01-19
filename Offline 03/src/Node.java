import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private final Course course;
    private final Set<Edge> edges;
    private int timeSlot;

    public Node(Course course) {
        this.course = course;
        edges = new HashSet<>();
        timeSlot = -1;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Course getCourse() {
        return course;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "Node{" +
                "course=" + course +
                ", timeSlot=" + timeSlot +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(course.getCourseNo());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node n)) return false;
        return course.getCourseNo() == n.getCourse().getCourseNo();
    }
}
