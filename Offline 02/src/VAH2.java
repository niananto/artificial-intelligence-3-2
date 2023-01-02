import java.util.HashSet;

//The variable chosen is the one with the maximum degree to unassigned
//variables. Also, called max-forward-degree

public class VAH2 extends VAH {
    public VAH2() {
        super();
    }

    @Override
    public Variable getAVariable() {
        // return the variable with the maximum forward degree count
        Variable currentMax = null;
        for (Variable v : unassignedVariables) {
            if (currentMax == null || v.forwardDegreeCount > currentMax.forwardDegreeCount) {
                currentMax = v;
            }
        }
        unassignedVariables.remove(currentMax);
//        System.out.println(unassignedVariables.size());
        return currentMax;
    }
}