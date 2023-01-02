import java.util.HashSet;
import java.util.Set;

public abstract class VAH {
    protected Set<Variable> unassignedVariables;

    public VAH() {
        this.unassignedVariables = new HashSet<>();
    }

    public abstract Variable getAVariable();
    public void addAVariable(Variable v) {
        unassignedVariables.add(v);
    }

    @Override
    public String toString() {
        return "VAH{" +
                "unassignedVariables=" + unassignedVariables +
                '}';
    }
}
