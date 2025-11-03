package edu.utexas.ece.llp;

import java.util.Arrays;

/**
 * Bellman–Ford (LLP variant): single-source shortest paths in a weighted digraph (no negative cycles).
 * Exposes the current distance vector via {@link #getSolution()}.
 *  
 * @author Abigail Johnson
 */
public class BellmanFord extends LLP {

    private final int[] G;       // distance labels
    private final int[][] pre;   // pre[j]: predecessors of j
    private final int[][] w;     // w[i][j]: weight of edge i -> j
    
    /** Constructs Bellman–Ford using vertex 0 as source. */
    public BellmanFord(int n, int[][] pre, int[][] w) {
        super(n);
        this.pre = pre;
        this.w = w;
        this.G = new int[n];
        Arrays.fill(G, INF);
        G[0] = 0;
    }
    
    /**
     * Enforces: G[j] <= min { G[i] + w[i,j] | i in pre(j) } for vertex j.
     * @return true if G[j] decreased
     */
    @Override
    public boolean ensure(int j) {
        int best = G[j];
        for (int i : pre[j]) {
            if (G[i] == INF) continue;
            best = min(best, G[i] + w[i][j]);
        }
        if (best < G[j]) { G[j] = best; return true; }
        return false;
    }

    /** Current distance vector G[0..n-1]. */
    public int[] getSolution() { return G; }
}