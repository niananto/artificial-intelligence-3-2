package Classes;

import java.util.Collection;

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
        System.out.println("timeslots: " + timeSlotCount);
        System.out.println("penalty: " + ((double)calculatePenalty() / (double)studentCount));
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
