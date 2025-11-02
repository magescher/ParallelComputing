import java.util.*;


public class Boruvka extends LLP {

    private final List<double[]> edges;

    private final int[] parent, rank;

    private final List<Integer>[] adj;

    private double[] bestW;
    private int[] bestU, bestV;

    private final List<double[]> mst = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public Boruvka(int n, List<double[]> undirectedEdges) {
        super(n);
        this.edges = undirectedEdges;
        this.parent = new int[n];
        this.rank   = new int[n];
        for (int i = 0; i < n; i++) { parent[i] = i; rank[i] = 0; }

        this.adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int ei = 0; ei < edges.size(); ei++) {
            int u = (int) edges.get(ei)[0];
            int v = (int) edges.get(ei)[1];
            adj[u].add(ei);
            adj[v].add(ei);
        }
    }

    // ---------- DSU helpers ----------
    private int find(int x) {
        while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
        return x;
    }
    private boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;
        if (rank[ra] < rank[rb]) parent[ra] = rb;
        else if (rank[ra] > rank[rb]) parent[rb] = ra;
        else { parent[rb] = ra; rank[ra]++; }
        return true;
    }

    // ---------- LLP overrides ----------

    @Override
    protected void onRoundStart(int round) {
        bestW = new double[n];
        Arrays.fill(bestW, Double.POSITIVE_INFINITY);
        bestU = new int[n]; Arrays.fill(bestU, -1);
        bestV = new int[n]; Arrays.fill(bestV, -1);
    }

    @Override
    public boolean forbidden(int j) {
        return find(j) == j;
    }

    @Override
    public void advance(int root) {
        int r = find(root);              // (re)check it is the root (cheap)
        if (r != root) return;

        
        for (int ei : adj[root]) {
            double[] e = edges.get(ei);
            int u = (int) e[0], v = (int) e[1];
            int ru = find(u), rv = find(v);
            if (ru == rv) continue; // internal edge
            // Outgoing edge from this component (root r)
            if (ru == r) {
                double w = e[2];
                synchronized (bestW) {
                    if (w < bestW[r]) { bestW[r] = w; bestU[r] = u; bestV[r] = v; }
                }
            } else if (rv == r) {
                double w = e[2];
                synchronized (bestW) {
                    if (w < bestW[r]) { bestW[r] = w; bestU[r] = v; bestV[r] = u; }
                }
            }
        }
    }

    /** Merge all chosen edges; return true iff any merge happened. */
    @Override
    protected boolean onRoundEnd(int round) {
        boolean any = false;
        for (int r = 0; r < n; r++) {
            int u = bestU[r], v = bestV[r];
            if (u == -1) continue;
            if (union(u, v)) {
                mst.add(new double[]{u, v, bestW[r]});
                any = true;
            }
        }
        return any;
    }

    // ---------- Results ----------
    public List<double[]> getMstEdges() { return mst; }

    /** Convenience: run until no more merges; return MST edge list. */
    public List<double[]> solve() {
        run(0); // unlimited rounds until convergence
        return mst;
    }
}
