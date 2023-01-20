package Classes.PerturbativeHeuristic;

import Classes.Node;
import Classes.Solution;

import java.util.ArrayList;

public class KempeChainInterchange implements IPH {
    private Solution solution;
    private ArrayList<KempeChain> kempeChains;

    public KempeChainInterchange(Solution prevSolution) {
        this.solution = prevSolution;
        this.kempeChains = new ArrayList<>();
    }

    @Override
    public Solution runPerturbation() {
        // create kempechains
        for (int i=1; i<=solution.getTimeSlotCount(); i++) {
            for (int j=1+1; j<=solution.getTimeSlotCount(); j++) {
                KempeChain kempeChain = new KempeChain(i, j);
                for (Node node : solution.getNodes()) {
                    if (node.getTimeSlot() == i || node.getTimeSlot() == j) {
                        kempeChain.addNode(node);
                    }
                }
                kempeChains.add(kempeChain);
            }
        }

        // should run kempe chain minimum 1000 times as long as it is reducing penalty)
        for (int i=0; i<1000; i++) {
            // Take a particular Kempe chain and swap the colors of all vertices
            KempeChain kempeChain = kempeChains.get((int) (Math.random() * kempeChains.size()));

            int oldPenalty = solution.calculatePenalty();
            kempeChain.interchange();
            int newPenalty = solution.calculatePenalty();

            if (newPenalty >= oldPenalty) break;
        }

        return solution;
    }
}
