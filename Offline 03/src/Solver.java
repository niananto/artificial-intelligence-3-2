public class Solver {
    private final ICH ch;
    private int totalPenalty;
    private int totalTimeslots;

    public Solver(ICH ch) {
        this.ch = ch;
        this.totalPenalty = 0;
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

            // update penalty [exponential](2^(5-n) for n<=5)(0 otherwise)
            for (Edge edge : node.getEdges()) {
                int n = Math.abs(edge.getOtherNode(node).getTimeSlot() - node.getTimeSlot());
                if (n <= 5) {
                    edge.setWeight((int) Math.pow(2, 5 - n));
                } else {
                    edge.setWeight(0);
                }
                totalPenalty += edge.getWeight();
            }

            // update ch
            ch.removeNode(node);
        }
    }

    public int getTotalPenalty() {
        return totalPenalty;
    }

    public int getTotalTimeslots() {
        return totalTimeslots;
    }

    public void printSolution() {
        System.out.println("Total penalty: " + totalPenalty);
        System.out.println("Total timeslots: " + totalTimeslots);
    }

    public void printSchedule() {
        for (int i = 1; i <= totalTimeslots; i++) {
            System.out.println("Timeslot " + i + ":");
//            System.out.println(ch.getNodes().size());
            for (Node node : ch.getAllNodes()) {
                if (node.getTimeSlot() == i) {
                    System.out.println(node.getCourse());
                }
            }
        }
    }
}
