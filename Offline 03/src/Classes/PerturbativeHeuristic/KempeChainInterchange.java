package Classes.PerturbativeHeuristic;

import Classes.Node;
import Classes.Random;
import Classes.Solution;

import java.util.ArrayList;
import java.util.List;

public class KempeChainInterchange implements IPH {
    private final Solution solution;

    public KempeChainInterchange(Solution prevSolution) {
        this.solution = new Solution(prevSolution);
    }

    @Override
    public Solution runPerturbation() {
        // should run kempe chain interchange minimum 1000 times
        // as long as it is reducing penalty
        for (int i=0; i<5; i++) {
            // take a random node
            // make the kempe chain from that
            // interchange the kempe chain
            // if it is not maintaining hard constraint or penalty is increasing, undo the interchange
            // else continue
            Node node = solution.getNodes().stream().toList().get((int) (Random.nextDouble() * solution.getNodes().size()));

            // iterate over its neighbours and make kempe chains for all of them
            for (Node neighbor : node.getNeighbours()) {
                KempeChain kempeChain = new KempeChain(node, neighbor);

                int oldPenalty = solution.calculatePenalty();
                kempeChain.interchange();
                int newPenalty = solution.calculatePenalty();

                if (!solution.maintainsHardConstraint() || newPenalty > oldPenalty) {
                    // undo the interchange
                    kempeChain.interchange();
                }
            }
        }

        return solution;
    }
}
