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
        Arrays.fill(G, -1 * INF);       // Init G[j] = - INF
        G[0] = -1                       // Dummy Value
        this.A = A;
        this.S = S;
    }

    /**
     *  See page 44 textbook
     *  - ensure: G[j] >= 0 if j = 1
     *  - ensure: G[j] >= G[j/2] if j is even
     *  - ensure: G[j] >= S[j - 1] + G[j/2] if j is odd and j < n
     *  - ensure: G[j] >= A[j - n] + G[j/2] if j is odd and j > n
     */
    @Override
    public boolean ensure(){
        if (j <= 0 || j >= vectorSize) return false;
        
        int old = G[j], best = old;
        if (j == 1) best = max(best, 0);
        else if (j % 2 == 0) best = max(best, G[j / 2]);
        else if (j < n) best = max(best, S[j - 1] + G[j / 2]);
        else best = max(best, A[j - n] + G[j / 2]);
        
        if (best > old) { G[j] = best; return true; }
        return false;
    }


    public int[] getSolution() {
        ensureSolved();
        int[] prefixSum = new int[n];
        for (int j = n; j < vectorSize; j++) prefixSum[j - n] = G[j];
        return prefixSum;
    }
}