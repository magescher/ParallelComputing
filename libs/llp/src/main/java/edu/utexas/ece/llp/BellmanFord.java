package edu.utexas.ece.llp;

import java.util.Arrays;

/**
 * Bellman–Ford (LLP variant): single-source shortest paths in a weighted digraph (no negative cycles).
 * Exposes the current distance vector via {@link #getSolution()}.
 *  
 * @author Abigail Johnson
 * 
 * input: pre(j): list of 1..n; w[i, j]: int for all i ↑ pre(j)
 * init: if (j = s) then G[j] == 0 else G[j] = maxint;
 * ensure: G[j] = min{G[i] + w[i, j] | i inc in pre(j)}
 */
public class BellmanFord extends LLP {

    private final int[] G;       // distance labels
    private final int[][] pre;   // pre[j]: predecessors of j
    private final int[][] w;     // w[i][j]: weight of edge i -> j
    private final int src;       // chosen source
    
    /** Default: source = 0 */
    public BellmanFord(int[][] pre, int[][] w) { this(pre, w, 0); }

    /** Constructs Bellman–Ford using vertex 0 as source. */
    public BellmanFord(int[][] pre, int[][] w, int src) {
        super(pre.length);       // Set vector size in parent class
        if (src < 0 || src >= vectorSize) throw new IllegalArgumentException("Source out of range");
        this.G = new int[vectorSize];
        Arrays.fill(G, INF);
        G[src] = 0;
        this.pre = pre;
        this.w = w;
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
    public int[] getSolution() { 
        ensureSolved();
        return G; 
    }
}