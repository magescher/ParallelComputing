package edu.utexas.ece.llp;

import java.util.Arrays;

/**
 * Parallel Prefix Sum (LLP Variant)
 * Exposes solution via {@link #getSolution()}.
 * 
 * @author Abigail Johnson
**/
public class PrefixSum extends LLP {
    
    private final int[] G, A, S;
    private final int n;                   
    
    public PrefixSum(int[] A, int[] S) {
        super(A.length * 2);
        this.n = A.length;
        if (S.length != n - 1) throw new IllegalArgumentException("S must have length N-1");
        G = new int[vectorSize];
        Arrays.fill(G, Integer.MIN_VALUE);       // Init G[j] = - INF
        this.A = A;
        this.S = S;
    }

    /**
     *  See page 44 textbook. Note below psuedo assumes 1 index. Implementation assumes 0
     *  - ensure: G[j] >= 0 if j = 1
     *  - ensure: G[j] >= G[j/2] if j is even
     *  - ensure: G[j] >= S[j - 1] + G[j/2] if j is odd and j < n
     *  - ensure: G[j] >= A[j - n] + G[j/2] if j is odd and j > n
     */
    @Override
    public boolean ensure(int j){
        int old = G[j];
        int best = old;
        
        // 1. root
        if (j == 1) best = max(best, 0);
        // 2. even: copy parent
        else if (j % 2 == 0 && j < n) best = max(best, G[j / 2]);
        // 3. odd internal: add left-subtree sum
        else if (j % 2 == 1 && j < n) best = max(best, G[j / 2] + S[(j - 3) / 2]);
        // 4. leaves: add element, and for right leaves include left sibling
        else if (j >= n) best = max(best, G[j / 2] + A[j - n] + ((j % 2 == 1) ? A[j - n - 1] : 0));

        G[j] = best;
        return G[j] != old;
    }


    public int[] getSolution() {
        ensureSolved();
        int[] prefixSum = new int[n];
        for (int i = n; i < vectorSize; i++)
            prefixSum[i - n] = G[i];
        return prefixSum;
    }
}