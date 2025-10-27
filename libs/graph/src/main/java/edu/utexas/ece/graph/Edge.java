package edu.utexas.ece.graph;

/**
 * Immutable edge representation (u, v, w).
 * Used by graph algorithms such as MST and shortest path.
 */
public class Edge {
    private final int u;
    private final int v;
    private final double w;

    public Edge(int u, int v, double w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public int u() { return u; }
    public int v() { return v; }
    public double w() { return w; }

    @Override
    public String toString() {
        return "(" + u + " -> " + v + ", w=" + w + ")";
    }
}
