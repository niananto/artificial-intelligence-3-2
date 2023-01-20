package Classes;

import Classes.ConstructiveHeuristic.ICH;

import java.util.*;

public class Solver {
    private final ICH ch;
    private Solution solution;

    public Solver(ICH ch, int studentCount, Collection<Edge> edges, Collection<Node> nodes) {
        this.ch = ch;
        ArrayList<Edge> newEdges = new ArrayList<>(edges);
        Set<Node> newNodes = new HashSet<>(nodes);
        this.solution = new Solution(studentCount, newEdges, newNodes);
    }

    public Solution solve() {
        int totalTimeslots = 0;

        while (ch.hasNext()) {
            Node node = ch.getNext();

            // find timeslot
            int timeslot = 1;
            boolean found = false;
            while(!found) {
                found = true;
                for (Edge edge : node.getEdges()) {
                    // ensuring the hard constraint
                    if (edge.getOtherNode(node).getTimeSlot() == timeslot) {
                        found = false;
                        timeslot++;
                        break;
                    }
                }
            }

            // set timeslot
            node.setTimeSlot(timeslot);
            totalTimeslots = Math.max(totalTimeslots, timeslot);
        }

        solution.setTimeSlotCount(totalTimeslots);
        return solution;
    }
}
