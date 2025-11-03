package edu.utexas.ece.llp;

import java.util.Arrays;

/**
 * FastComponents (LLP variant): computes connected components 
 * using parallel pointer jumping and label propagation.
 * Exposes the component labels via {@link #getSolution()}.
 * 
 * @author Abigail Johnson
 */
public class FastComponents extends LLP {

    private final int[] G;      // component labels
    private final int[][] adj;  // adj[j]: neighbors of j

    /** Constructs FastComponents with initial labels G[j] = j. */
    public FastComponents(int n, int[][] adj) {
        super(n);
        this.adj = adj;
        this.G = new int[n];
        for (int j = 0; j < n; j++) G[j] = j;  // Init: G[j] = j
    }

    /**
     * Enforces pointer jumping and label propagation for vertex j.
     * @return true if G[j] changed
     */
    @Override
    public boolean ensure(int j) {
        int old = G[j];
        // Pointer jumping step
        if (G[j] != G[G[j]]) { G[j] = G[G[j]];} 
        // Label propagation
        else {
            for (int i : adj[j])
                if (G[i] > G[j]) G[j] = G[i];
        }
        return G[j] != old;
    }

    /** Returns the current component labels G[0..n-1]. */
    public int[] getSolution() { return G; }
}
