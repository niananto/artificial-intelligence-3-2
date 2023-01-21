package Classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Solution {
    private final int studentCount;
    private final Collection<Edge> edges;
    private final Collection<Node> nodes;
    private int timeSlotCount;

    public Solution(int studentCount, Collection<Edge> edges, Collection<Node> nodes) {
        this.studentCount = studentCount;
        this.edges = edges;
        this.nodes = nodes;

        this.timeSlotCount = -1;
    }

    public Solution(Solution solution) {
        this.studentCount = solution.studentCount;
        this.edges = new ArrayList<>(solution.edges);
        this.nodes = new HashSet<>(solution.nodes);
        this.timeSlotCount = solution.timeSlotCount;
    }

    public boolean maintainsHardConstraint() {
        for (Edge edge : edges) {
            if (!edge.maintainsHardConstraint()) return false;
        }
        return true;
    }

    public void setTimeSlotCount(int timeSlotCount) {
        this.timeSlotCount = timeSlotCount;
    }

    public int getTimeSlotCount() {
        return timeSlotCount;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public int calculatePenalty() {
        int totalPenalty = 0;
        for (Edge edge : edges) {
            int penalty = edge.calculatePenalty();
            if (penalty >= 0) {
                totalPenalty += penalty;
            } else {
                System.out.println("Error: penalty calculation error");
            }
        }
        return totalPenalty;
    }

    public void printSolution() {
        System.out.println("\ttimeslots: " + timeSlotCount);
        System.out.println("\tpenalty: " + ((double)calculatePenalty() / (double)studentCount));
    }

    public void printSchedule() {
        for (int i = 1; i <= timeSlotCount; i++) {
            System.out.println("Timeslot " + i + ":");
            for (Node node : nodes) {
                if (node.getTimeSlot() == i) {
                    System.out.println(node.getCourse());
                }
            }
        }
    }
}
