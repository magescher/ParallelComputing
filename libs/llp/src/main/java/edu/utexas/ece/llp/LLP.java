package edu.utexas.ece.llp;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public abstract class LLP {
    
    protected final int n;

    protected LLP(int n) { this.n = n;}

    protected boolean forbidden(int j) { return false; }
    
    protected void advance(int j) { }

    protected boolean ensure(int j) {
        if (forbidden(j)) { 
            advance(j); 
            return true; 
        }
        return false;
    }

    /** 
     * Always-parallel fixpoint loop.
     * Runs until no component changes in a full round.
     */
    public final void solve() {
        while (true) {
            AtomicBoolean changed = new AtomicBoolean(false);
            IntStream.range(0, n).parallel().forEach(j -> {
                if (ensure(j)) changed.set(true);
            });
            if ( !changed.get() ) return;  // reached a fixpoint
        }
    }
}
