// The variable chosen is the one that minimizes the VAH1 / VAH2

public class VAH4 extends VAH{
    public VAH4() {
        super();
    }

    @Override
    public Variable getAVariable() {
        Variable var = null;
        for (Variable v : unassignedVariables) {
            if (var == null || (v.getDomainCount()/v.forwardDegreeCount) < (var.getDomainCount()/var.forwardDegreeCount)) {
                var = v;
            }
        }
        unassignedVariables.remove(var);
        System.out.println(unassignedVariables.size());
        return var;
    }
}
