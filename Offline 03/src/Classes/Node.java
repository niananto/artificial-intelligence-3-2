package Classes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private final Course course;
    private final Set<Edge> edges;
    private int timeSlot;
    private int saturationDegree; // for DSatur algorithm

    public Node(Course course) {
        this.course = course;
        edges = new HashSet<>();
        timeSlot = -1;
        saturationDegree = 0;
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

    public boolean hasNeighbour() {
        return !edges.isEmpty();
    }

    public Collection<Node> getNeighbours() {
        Collection<Node> neighbours = new HashSet<>();
        for (Edge edge : edges) {
            neighbours.add(edge.getOtherNode(this));
        }
        return neighbours;
    }

    public boolean hasTimeSlot() {
        return timeSlot != -1;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getSaturationDegree() {
        return saturationDegree;
    }

    public void increaseSaturationDegree() {
        saturationDegree++;
    }

    public int getEnrollmentCount() {
        return course.getNoOfStudents();
    }

    @Override
    public String toString() {
        return "Classes.Node{" +
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
