package edu.utexas.ece.llp;

/**
 * Stable Marriage (LLP variant): man-optimal matching.
 */
public class StableMarriage extends LLP {

    private final int m, w;
    private final int[][] menPref;     // menPref[j][k]: man j's k-th preferred woman
    private final int[][] womenPref;   // womenPref[z][i]: rank of man i for woman z
    private final int[] G;             // current proposal index per man

    public StableMarriage(int[][] menPref, int[][] womenPref) {
        super(menPref.length);
        this.m = menPref.length;
        this.w = womenPref.length;
        this.menPref = menPref;
        this.womenPref = womenPref;
        this.G = new int[m];  // Init: G[j] = 0
    }

    @Override
    protected boolean forbidden(int j) {
        int z = menPref[j][G[j]];      // woman j is currently proposing to
        int[] womenRank = womenPref[z];    // woman's preference over men
        for (int i = 0; i < m; i++) {
            if (i == j) continue;
            if (menPref[i][G[i]] == z && womenRank[i] < womenRank[j]) return true; // z prefers i over j
        }
        return false;
    }

    @Override
    // man j moves to next woman on his list
    protected void advance(int j) { G[j]++; }

    /** Returns man-optimal assignment: assignment[j] = woman matched to man j. */
    public int[] getSolution() {
        int[] assignment = new int[m];
        for (int j = 0; j < m; j++) assignment[j] = menPref[j][G[j]];
        return assignment;
    }
}

