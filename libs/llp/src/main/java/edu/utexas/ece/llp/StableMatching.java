package edu.utexas.ece.llp;

/**
 * Stable Matching (LLP variant): man-optimal matching.
 * Exposes proposal assignment via {@link #getSolution()}.
 */
public class StableMatching extends LLP {

    private final int[] G;                      // current proposal index per man
    private final int[][] womenPref, menPref;   // preference lists
    private final int numMen;

    public StableMatching(int[][] menPref, int[][] womenPref) {
        super(numMen);
        this.numMen = menPref.length;
        this.G = new int[numMen];  // Init: G[j] = 0
        this.womenPref = womenPref;
        this.menPref = menPref;
    }

    @Override
    protected boolean forbidden(int j) {
        int[] womenRank = womenPref[z];           // woman's preference over men
        int prospect = menPref[j][G[j]];          // woman j is currently proposing to
        for (int i = 0; i < numMen; i++) {
            if (i != j && menPref[i][G[i]] == prospect && womenRank[i] < womenRank[j]) return true; // z prefers i over j
        }
        return false;
    }

    @Override
    // man j moves to next woman on his list
    protected void advance(int j) { G[j]++; }

    /** Returns man-optimal proposal assignment: assignment[j] = woman matched to man j. */
    public int[] getSolution() {
        int[] proposals = new int[numMen];
        for (int j = 0; j < numMen; j++) proposals[j] = menPref[j][G[j]];
        return proposals;
    }
}

