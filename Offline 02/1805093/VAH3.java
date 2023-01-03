// The variable chosen by VAH1, Ties are broken by VAH2

public class VAH3 extends VAH{
    public VAH3() {
        super();
    }

    @Override
    public Variable getAVariable() {
        Variable var = null;
        for (Variable v : unassignedVariables) {
            if (var == null || v.getDomainCount() < var.getDomainCount()) {
                var = v;
            } else if (v.getDomainCount() == var.getDomainCount()) {
                if (v.forwardDegreeCount > var.forwardDegreeCount) {
                    var = v;
                }
            }
        }
        unassignedVariables.remove(var);
//        System.out.println(unassignedVariables.size());
        return var;
    }
}
