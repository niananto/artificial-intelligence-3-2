import java.util.Set;

public abstract class CSP {
    protected int N;
    protected VAH vah;
    protected Variable[][] variables;

    public Variable[][] solution;
    public long totalNodes;
    public long backtracks;

    public CSP(int N, VAH vah, Variable[][] variables) {
        this.solution = null;
        this.N = N;
        this.vah = vah;
        this.variables = variables;

        this.totalNodes = 0;
        this.backtracks = 0;
    }

    public abstract boolean solverStart();

    protected abstract Set<Variable> update(Variable[][] variables, Variable variable);
    protected abstract void revertUpdate(Variable variable, Set<Variable> updatedVariables);
}
