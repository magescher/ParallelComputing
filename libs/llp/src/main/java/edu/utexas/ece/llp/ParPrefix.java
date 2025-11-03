package edu.utexas.ece.llp;

import java.util.Arrays;

public class ParPrefix extends LLP {
    
    int[] G;
    final int n;
    final int[] A;
    final int[] S;
    
    public ParPrefix(int[] A, int[] S) {
        super(A.length * 2);
        this.n = A.length;
        this.A = A;
        this.S = S;
        G = new int[2 * n];
        Arrays.fill(G, Integer.MIN_VALUE);
        G[0] = -1;
    }

    @Override
    public boolean forbidden(int j) {
        return ((j == 1 && G[j] < 0) ||
        (j % 2 == 0 && G[j] < G[j/2]) ||
        (j % 2 == 1 && j > 1 && j < n && G[j] < S[j-2] + G[j/2]) ||
        (j % 2 == 1 && j > 1 && j > n && G[j] < A[j-n-1] + G[j/2]));
    }

    @Override
    public void advance(int j) {
        if (j == 1) G[j] = 0;
        else if (j % 2 == 0) G[j] = G[j/2];
        else if (j % 2 == 1 && j < n) G[j] = S[j-2] + G[j/2];
        else if (j % 2 == 1 && j > n) G[j] = A[j-n-1] + G[j/2];
    }

    public int[] getSolution() {
        int[] prefix = new int[n];
        for (int i = n; i < 2*n; i++)
            prefix[i-n] = G[i];
        return prefix;
    }
}