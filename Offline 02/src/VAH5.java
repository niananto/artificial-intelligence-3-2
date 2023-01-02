// A random unassigned variable is chosen

import java.util.Random;

public class VAH5 extends VAH {
    public VAH5() {
        super();
    }

    @Override
    public Variable getAVariable() {
        if (unassignedVariables.isEmpty()) return null;

//        Variable var = null;
//        int index = (int) (Math.random() * unassignedVariables.size());
//        var = unassignedVariables.toArray()[index];
//        unassignedVariables.remove(var);
//        System.out.println(unassignedVariables.size());
//        return var;

        int size = unassignedVariables.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Variable v : unassignedVariables)
        {
            if (i == item) {
                unassignedVariables.remove(v);
                return v;
            }
            i++;
        }
        return null;
    }
}
