import java.util.HashSet;

//The variable chosen is the one with the smallest domain

public class VAH1 extends VAH {
    public VAH1() {
        super();
    }

    @Override
    public Variable getAVariable() {
        Variable var = null;
        for (Variable v : unassignedVariables) {
            if (var == null || v.getDomainCount() < var.getDomainCount()) {
                var = v;
            }
        }
        unassignedVariables.remove(var);
        System.out.println(unassignedVariables.size());
        return var;
    }
}
