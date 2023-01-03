import java.util.HashSet;
import java.util.Set;

public class BTSolver extends CSP {

    public BTSolver(int N, VAH vah, Variable[][] variables) {
        super(N, vah, variables);
    }

    @Override
    public boolean solverStart() {
        return solver();
    }

    private boolean solver() {
//        System.out.println(vah.unassignedVariables.size());
        Variable var = vah.getAVariable();
        if (var == null) {
            // all variables are assigned
            this.solution = variables;
            return true;
        }

        boolean[] initialDomain = new boolean[N];
        System.arraycopy(var.domain, 0, initialDomain, 0, N);
//        System.out.println(var + " " + Arrays.toString(var.domain));
//        int value = var.getAValue();
        int value = var.getAValueLeastConstraining(variables);
        totalNodes++;

        while (value >= 0) {
//            System.out.println("Trying value " + (value + 1) + " for variable " + var);
            Set<Variable> updatedVariables = update(variables, var);

            if (solver()) {
                return true;
            } else {
                revertUpdate(var, updatedVariables);
//                var.revertAValue(value);
            }

//            System.out.println(var + " " + Arrays.toString(var.domain));
//            value = var.getAValue();
            value = var.getAValueLeastConstraining(variables);
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
        // update the domain of all variables in the same row and column
        // and store those in a set
        // and update the forward degree count of all variables in the same row and column
        Set<Variable> updatedVariables = new HashSet<>();
        for (int i = 0; i < variable.N; i++) {
            variables[variable.x][i].forwardDegreeCount--;
            if (variables[variable.x][i].domain != null && variables[variable.x][i].domain[variable.value] && variables[variable.x][i].value == -1) {
                variables[variable.x][i].domain[variable.value] = false;
                updatedVariables.add(variables[variable.x][i]);
            }
            variables[i][variable.y].forwardDegreeCount--;
            if (variables[i][variable.y].domain != null && variables[i][variable.y].domain[variable.value] && variables[i][variable.y].value == -1) {
                variables[i][variable.y].domain[variable.value] = false;
                updatedVariables.add(variables[i][variable.y]);
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
