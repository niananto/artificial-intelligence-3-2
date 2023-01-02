import java.util.Objects;

public class Variable {
    public int N;
    public int x;
    public int y;
    public int value;
    // domain boolean array
    // true means available
    public boolean[] domain;
    // forward degree count
    // initial 2N-2 or 2N
    // when assigning a value to a variable, the forward degree count of all its rows' and columns' is reduced by 1
    public int forwardDegreeCount;

    // for unassigned variables
    public Variable(int N, int x, int y, boolean[] rowDomain, boolean[] colDomain) {
        this.N = N;
        this.x = x;
        this.y = y;

        // -1 means unassigned
        this.value = -1;
        this.domain = new boolean[N];
        for (int i=0; i<N; i++) {
            this.domain[i] = rowDomain[i] && colDomain[i];
        }
        this.forwardDegreeCount = 2*N + 1;
    }

    // for assigned variables
    public Variable(int N, int x, int y, int value) {
        this.N = N;
        this.x = x;
        this.y = y;
        this.value = value;
        this.domain = null;
        this.forwardDegreeCount = 2*N + 1;
    }

    public int getDomainCount() {
        int count = 0;
        for (int i=0; i<this.N; i++) {
            if (this.domain[i]) count++;
        }
        return count;
    }

    // Value Heuristic
     public int getAValue() {
        if (this.domain == null) return -1;
        for (int i=0; i<this.N; i++) {
            if (this.domain[i]) {
                this.domain[i] = false;
                this.value = i;
                return i;
            }
        }
        this.value = -1;
        return -1;
     }

     public void revertAValue(int value) {
        this.value = -1;
     }

     public int getAValueLeastConstraining(Variable[][] variables) {
        if (this.domain == null) return -1;
        int[] count = new int[this.N];
        for (int i=0; i<this.N; i++) {
            if (this.domain[i]) {
                for (int j=0; j<this.N; j++) {
                    if (variables[this.x][j].domain != null && variables[this.x][j].domain[i]) count[i]++;
                    if (variables[j][this.y].domain != null && variables[j][this.y].domain[i]) count[i]++;
                }
            }
        }
        int min = -1;
        for (int i=0; i<this.N; i++) {
            if (this.domain[i] && (min == -1 || count[i] < count[min])) {
                min = i;
            }
        }
        if (min != -1) {
            this.domain[min] = false;
            this.value = min;
        }
        return min;
     }


    @Override
    public String toString() {
        return "Variable{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + (value+1) +
                '}';
    }

    @Override
    public int hashCode() {
        return x*17 + y;
    }
}