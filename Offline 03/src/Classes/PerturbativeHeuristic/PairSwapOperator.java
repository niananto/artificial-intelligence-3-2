package Classes.PerturbativeHeuristic;

import Classes.Node;
import Classes.Solution;

import java.util.ArrayList;
import java.util.List;

public class PairSwapOperator implements IPH {
    private final Solution solution;
    private List<KempeChain> kempeChains;
    private final List<KempeChain> oneMemberChains;

    public PairSwapOperator(Solution prevSolution) {
        this.solution = new Solution(prevSolution);
        this.kempeChains = null;
        this.oneMemberChains = new ArrayList<>();
    }

    public PairSwapOperator(Solution prevSolution, List<KempeChain> kempeChains) {
        this.solution = prevSolution;
        this.kempeChains = new ArrayList<>(kempeChains);
        this.oneMemberChains = new ArrayList<>();
    }

    @Override
    public Solution runPerturbation() {
        // if kempeChains is null, then create the kempe chains
        if (kempeChains == null) {
            kempeChains = new ArrayList<>();

            for (int i = 1; i <= solution.getTimeSlotCount(); i++) {
                for (int j = 1 + 1; j <= solution.getTimeSlotCount(); j++) {
                    KempeChain kempeChain = new KempeChain(i, j);
                    for (Node node : solution.getNodes()) {
                        if (node.getTimeSlot() == i || node.getTimeSlot() == j) {
                            kempeChain.addNode(node);
                        }
                    }
                    kempeChains.add(kempeChain);
                }
            }
        }

        // now that I have the kempe chains
        // keep one-member chains only
        for (KempeChain kempeChain : kempeChains) {
            if (kempeChain.isOneMember()) oneMemberChains.add(kempeChain);
        }

        // if not even two one-member chains, return
        if (oneMemberChains.size() < 2) return solution;

        // should run pair-swap minimum 1000 times as long as it is reducing penalty
        for (int i=0; i<10; ) {
            // now take two one-member chains who are pair swappable
            KempeChain kc1 = oneMemberChains.get((int) (Math.random() * oneMemberChains.size()));
            KempeChain kc2 = null;

            // randomly search for 80% times of the whole size
            for (int j = 0; j < oneMemberChains.size() * 0.8; j++) {

                kc2 = oneMemberChains.get((int) (Math.random() * oneMemberChains.size()));
                if (kc1.equals(kc2)) continue;
                if (!kc1.isPairSwappableWith(kc2)) continue;

                // once I find two swappable pairs -
                break;
            }

            // if we don't find any swappable pairs
            if (kc2 == null) continue;

            int oldPenalty = solution.calculatePenalty();
            kc1.pairSwap(kc2);
            int newPenalty = solution.calculatePenalty();

            if (newPenalty > oldPenalty) {
                // revert pair swap
                kc1.pairSwap(kc2);
                continue;
            }

            i++;
        }

        return solution;
    }
}
