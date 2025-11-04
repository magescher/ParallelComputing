package edu.utexas.ece.llp;

import java.util.*;

public class Boruvka extends LLP {
    public static record Edge(int u,int v,int w) {}
    private final List<Edge> E, T = new ArrayList<>();
    private final int[] G;

    public Boruvka(int n, List<Edge> edges) {
        super(n);
        this.E = edges;
        this.G = new int[vectorSize];
        for (int i = 0; i < vectorSize; i++) G[i] = i;
    }

    private int rep(int x){ 
        while (G[x] != x) x = G[x]; 
        return x; 
    }

    @Override
    protected boolean ensure(int j) {
        if (rep(j) != j) return false;
        Edge best = null;
        for (Edge e : E) {
            int a = rep(e.u), b = rep(e.v);
            if (a == b || (a != j && b != j)) continue;
            if (best == null || e.w < best.w) best = e;
        }
        if (best == null) return false;
        int a = rep(best.u), b = rep(best.v);
        if (a == b) return false;
        int lo = min(a,b), hi = max(a,b);
        if (j != lo) return false;     // only lower-ID rep merges & records
        G[hi] = lo; 
        T.add(best);
        return true;
    }

    public List<Edge> getSolution() {
        ensureSolved();                 
        return T;                       // if connected: |T| = n-1 (MST); else MSF
    }
}

