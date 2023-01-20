package Classes;

import Classes.ConstructiveHeuristic.ICH;

public class Solver {
    private final ICH ch;
    private int totalTimeslots;

    public Solver(ICH ch) {
        this.ch = ch;
        this.totalTimeslots = 0;
    }

    public void solve() {
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
    }

    public int getTotalTimeslots() {
        return totalTimeslots;
    }

    public void printSchedule() {
        for (int i = 1; i <= totalTimeslots; i++) {
            System.out.println("Timeslot " + i + ":");
            for (Node node : ch.getAllNodes()) {
                if (node.getTimeSlot() == i) {
                    System.out.println(node.getCourse());
                }
            }
        }
    }
}
