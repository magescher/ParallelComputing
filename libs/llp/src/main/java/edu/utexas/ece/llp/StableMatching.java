package edu.utexas.ece.llp;

/**
 * Stable Matching (LLP variant): man-optimal matching.
 * Exposes proposal assignment via {@link #getSolution()}.
 * 
 *  @author Abigail Johnson
 *  Inspired from implementation by @author Vijay K Garg in "A Systematic Approach to Sequential Algorithms"
 */
public class StableMatching extends LLP {

    private final int[] G;                      // current proposal index per man
    private final int[][] womenPref, menPref;   // preference lists

    public StableMatching(int[][] menPref, int[][] womenPref) {
        super(menPref.length);                  // Set vector size in parent class
        this.G = new int[vectorSize];           // Init: G[j] = 0
        this.womenPref = womenPref;
        this.menPref = menPref;
    }

    @Override
    protected boolean forbidden(int j) {
        int prospect = menPref[j][G[j]];         // woman j is currently proposing to
        int[] womenRank = womenPref[prospect];   // woman's preference over men
        for (int i = 0; i < vectorSize; i++) {
            if (i != j && menPref[i][G[i]] == prospect && womenRank[i] < womenRank[j]) return true; // z prefers i over j
        }
        return false;
    }

    @Override
    // man j moves to next woman on his list
    protected void advance(int j) { G[j]++; }

    /** Returns man-optimal proposal assignment: assignment[j] = woman matched to man j. */
    public int[] getSolution() {
        ensureSolved();
        int[] proposals = new int[vectorSize];
        for (int j = 0; j < vectorSize; j++) proposals[j] = menPref[j][G[j]];
        return proposals;
    }
}

