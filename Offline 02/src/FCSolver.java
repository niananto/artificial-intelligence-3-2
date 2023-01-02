import java.util.HashSet;
import java.util.Set;

public class FCSolver extends CSP {
    private boolean isConsistent = true;

    public FCSolver(int N, VAH vah, Variable[][] variables) {
        super(N, vah, variables);
    }

    @Override
    public boolean solverStart() {
        return solver();
    }

    private boolean solver() {
        Variable var = vah.getAVariable();
        if (var == null) {
            this.solution = variables;
            return true;
        }

        boolean[] initialDomain = new boolean[N];
        System.arraycopy(var.domain, 0, initialDomain, 0, N);
        int value = var.getAValue();
        totalNodes++;

        while (value >= 0) {
            isConsistent = true;

//            System.out.println("Trying value " + (value + 1) + " for variable " + var);
            Set<Variable> updatedVariables = update(variables, var);

            // Forward Checking
            if (isConsistent && solver()) {
                return true;
            } else { // either one of them are false
                revertUpdate(var, updatedVariables);
            }

            value = var.getAValue();
            totalNodes++;
        }
        backtracks++;

//        System.out.println("No value found for " + var);
        var.domain = initialDomain;
        vah.addAVariable(var);

        return false;
    }

    @Override
    protected Set<Variable> update(Variable[][] variables, Variable variable) {
        Set<Variable> updatedVariables = new HashSet<>();
        for (int i = 0; i < variable.N; i++) {
            variables[variable.x][i].forwardDegreeCount--;
            if (variables[variable.x][i].domain != null && variables[variable.x][i].domain[variable.value] && variables[variable.x][i].value == -1) {
                variables[variable.x][i].domain[variable.value] = false;
                updatedVariables.add(variables[variable.x][i]);
                if (variables[variable.x][i].getDomainCount() == 0) {
                    isConsistent = false;
                    return updatedVariables;
                }
            }
            variables[i][variable.y].forwardDegreeCount--;
            if (variables[i][variable.y].domain != null && variables[i][variable.y].domain[variable.value] && variables[i][variable.y].value == -1) {
                variables[i][variable.y].domain[variable.value] = false;
                updatedVariables.add(variables[i][variable.y]);
                if (variables[i][variable.y].getDomainCount() == 0) {
                    isConsistent = false;
                    return updatedVariables;
                }
            }
        }
        return updatedVariables;
    }

    @Override
    protected void revertUpdate(Variable variable, Set<Variable> updatedVariables) {
        // withdraw current update
        for (Variable v : updatedVariables) {
            v.domain[variable.value] = true;
        }

        // update forward degree count
        for (int i = 0; i < variable.N; i++) {
            variables[variable.x][i].forwardDegreeCount++;
            variables[i][variable.y].forwardDegreeCount++;
        }
    }
}
