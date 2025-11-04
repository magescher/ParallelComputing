package edu.utexas.ece.llp;

import java.util.Arrays;

/**
 * Johnson (LLP variant): computes feasible vertex potentials for reweighting edges
 * in a weighted digraph (no negative cycles).
 * Exposes the current potential vector via {@link #getSolution()}.
 *
 * @author Abigail Johnson
 * Inspired from Algorithm Provided by @author Vijay K Garg in "A Systematic Approach to Sequential Algorithms"
 */
public class Johnson extends LLP {

    private final int[] G;       // distance labels
    private final int[][] pre;   // pre[j]: predecessors of j
    private final int[][] w;     // w[i][j]: weight of edge i -> j

    /** Constructs Johnson’s algorithm (LLP form). */
    public Johnson(int[][] pre, int[][] w) {
        super(pre.length);      // Set vector size in parent class
        this.G = new int[vectorSize];
        Arrays.fill(G, 0);
        this.pre = pre;
        this.w = w;
    }

    /**
     * Enforces: G[j] >= max { G[i] - w[i,j] | i in pre(j) } for vertex j.
     * @return true if G[j] increased
     */
    @Override
    public boolean ensure(int j) {
        int best = G[j];
        for (int i : pre[j]) best = max(best, G[i] - w[i][j]);
        if (best > G[j]) { G[j] = best; return true; }
        return false;
    }

    /** Current potential vector G[0..n-1]. */
    public int[] getSolution() { 
        ensureSolved();
        return G; 
    }
}
