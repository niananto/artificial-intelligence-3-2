// A random unassigned variable is chosen

import java.util.Random;

public class VAH5 extends VAH {
    public VAH5() {
        super();
    }

    @Override
    public Variable getAVariable() {
        if (unassignedVariables.isEmpty()) return null;

        // get a random variable from unassignedVariables
        Variable var = null;
        int index = (int) (Math.random() * unassignedVariables.size());
        int i = 0;
        for (Variable v : unassignedVariables) {
            if (i == index) {
                var = v;
                break;
            }
            i++;
        }
        unassignedVariables.remove(var);
        return var;
    }
}
