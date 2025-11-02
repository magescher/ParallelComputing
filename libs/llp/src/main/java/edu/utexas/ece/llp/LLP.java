package edu.utexas.ece.llp;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public abstract class LLP {
    
    protected final int n;
    protected static final int INF = 1_000_000_000; // 1e9, acts as "infinity"

    protected LLP(int n) { this.n = n; }

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
        while (true) {
            AtomicBoolean changed = new AtomicBoolean(false);
            IntStream.range(0, n).parallel().forEach(j -> {
                if (ensure(j)) changed.set(true);
            });
            if (!changed.get()) return;
        }
    }
}

