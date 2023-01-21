package Classes.PerturbativeHeuristic;

import Classes.Node;
import Classes.Random;
import Classes.Solution;

public class PairSwapOperator implements IPH {
    private final Solution solution;

    public PairSwapOperator(Solution prevSolution) {
        this.solution = new Solution(prevSolution);
    }

    @Override
    public Solution runPerturbation() {
        int pairSwappableCount = 0;

        // should run pair-swap minimum 1000 times as long as it is reducing penalty
        for (int i=0; i<1000; i++) {
            // take two random nodes
            // make the kempe chain from that
            // if they are pair swappable, swap them
            // if it is not maintaining hard constraint or penalty is increasing, undo the interchange
            // else continue
            Node node1 = solution.getNodes().stream().toList().get((int) (Random.nextDouble() * solution.getNodes().size()));
            Node node2 = solution.getNodes().stream().toList().get((int) (Random.nextDouble() * solution.getNodes().size()));
            KempeChain kc1 = new KempeChain(node1, node2);
            KempeChain kc2 = new KempeChain(node2, node1);

            if (kc1.isPairSwappableWith(kc2)) {
                pairSwappableCount++;
                int oldPenalty = solution.calculatePenalty();
                kc1.pairSwapWith(kc2);
                int newPenalty = solution.calculatePenalty();

                if (!solution.maintainsHardConstraint() || newPenalty > oldPenalty) {
                    // undo the interchange
                    kc1.pairSwapWith(kc2);
                }
            }
        }

//        System.out.println("\t\tPair swappable count: " + pairSwappableCount);
        return solution;
    }
}
