package edu.utexas.ece.llp;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * LLP Solver
 *  
 * @author Abigail Johnson
 */
public abstract class LLP {
    
    protected final int vectorSize;
    protected static final int INF = 1_000_000_000; // 1e9, acts as "infinity"
    
    // True once the algorithm has reached a fixed point
    private final AtomicBoolean isSolved = new AtomicBoolean(false);

    protected LLP(int vectorSize) { this.vectorSize = vectorSize; }

    // Utilities
    protected static int min(int a, int b) { return (a <= b) ? a : b; }
    protected static int max(int a, int b) { return (a >= b) ? a : b; }

    // Optional hooks for subclasses that use forbidden/advance
    protected boolean forbidden(int j) { return false; }
    protected void advance(int j) {}

    // Default ensure delegates to forbidden/advance
    protected boolean ensure(int j) {
        if (forbidden(j)) { advance(j); return true; }
        return false;
    }

    // Always-parallel fixpoint loop
    public final void solve() {
        if (isSolved.get()) return;  // Already solved — skip
        while (true) {
            AtomicBoolean update = new AtomicBoolean(false);
            IntStream.range(0, vectorSize).parallel().forEach(j -> {
                if (ensure(j)) update.set(true);
            });
            if (!update.get()) {
                isSolved.set(true);  // Mark as done
                return;
            }
        }
    }

    protected final void ensureSolved() { solve(); }
}

