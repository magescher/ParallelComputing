import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Bulk-synchronous LLP engine base.
 *
 * Two ways to extend:
 *  (A) Override both forbidden(int j) and advance(int j).
 *      The engine will detect all forbidden j in a round, then advance them in parallel.
 *
 *  (B) Override ensure(int j) to perform j's local update and return true if it changed state.
 *      The engine will call ensure(j) for all j in parallel each round and stop at convergence.
 *
 * Optional phase hooks (for algorithms like Borůvka):
 *  - onRoundStart(int round)         // reset round-scratch
 *  - onRoundEnd(int round) -> bool   // return true if global progress (e.g., merges) occurred
 *
 * Stop condition: no local changes AND onRoundEnd returned false.
 *
 * Usage:
 *   class FastComponent extends LLP { override ensure(...) { ... } }
 *   class Traversal     extends LLP { override forbidden(...), advance(...) { ... } }
 */
public abstract class LLP {
    protected final int n;

    public LLP(int n) { this.n = n; }

    /* ================================
     *  Style A: forbidden/advance API
     *  (override both in subclasses that prefer this style)
     * ================================ */
    protected boolean forbidden(int j) {
        throw new UnsupportedOperationException("Override ensure(j) OR both forbidden(j) and advance(j).");
    }

    protected void advance(int j) {
        throw new UnsupportedOperationException("Override ensure(j) OR both forbidden(j) and advance(j).");
    }

    /* ================================
     *  Style B: ensure API
     *  (override in subclasses that prefer single-step updates)
     * ================================ */
    protected boolean ensure(int j) {
        // Default bridge: if subclass did not override ensure(j) but DID override forbidden/advance,
        // emulate ensure by calling forbidden→advance.
        if (usesForbiddenAdvance()) {
            if (forbidden(j)) { advance(j); return true; }
            return false;
        }
        // Otherwise subclasses must override ensure(j)
        throw new UnsupportedOperationException("Subclass must override ensure(j) or (forbidden+advance).");
    }

    /* ================================
     *  Optional round hooks
     * ================================ */
    protected void onRoundStart(int round) {}
    /** @return true if any global progress (e.g., merges) happened at phase end */
    protected boolean onRoundEnd(int round) { return false; }

    /* ================================
     *  Engine
     * ================================ */

    /**
     * Run LLP rounds until convergence or the given cap.
     * @param maxRounds <=0 means unlimited rounds.
     * @return number of rounds executed.
     */
    public final int run(int maxRounds) {
        final int cap = (maxRounds <= 0) ? Integer.MAX_VALUE : maxRounds;
        final boolean modeA = usesForbiddenAdvance();

        int round = 0;
        for (; round < cap; round++) {
            onRoundStart(round);

            AtomicBoolean changed = new AtomicBoolean(false);

            if (modeA) {
                // Style A: detect forbidden set, then advance them (bulk-synchronous round)
                final boolean[] todo = new boolean[n];
                AtomicBoolean anyForbidden = new AtomicBoolean(false);

                // 1) detect
                IntStream.range(0, n).parallel().forEach(j -> {
                    if (forbidden(j)) { todo[j] = true; anyForbidden.set(true); }
                });

                if (!anyForbidden.get()) {
                    // No local work; allow end-of-round global commit, then possibly stop
                    boolean global = onRoundEnd(round);
                    if (!global) break;
                    else continue;
                }

                // 2) advance all forbidden in parallel
                IntStream.range(0, n).parallel().forEach(j -> {
                    if (todo[j]) {
                        advance(j);
                        changed.set(true);
                    }
                });
            } else {
                // Style B: call ensure(j) for all j in parallel
                IntStream.range(0, n).parallel().forEach(j -> {
                    if (ensure(j)) changed.set(true);
                });
            }

            boolean global = onRoundEnd(round);

            // Stop if neither local changes nor end-of-round global changes occurred
            if (!changed.get() && !global) break;
        }
        return round;
    }

    /** Convenience: run to convergence (no round cap). */
    public final int run() { return run(0); }

    /* ================================
     *  Helper: detect whether subclass overrode forbidden/advance
     * ================================ */
    private Boolean cachedFA = null;
    private boolean usesForbiddenAdvance() {
        if (cachedFA != null) return cachedFA;
        try {
            var f = getClass().getDeclaredMethod("forbidden", int.class);
            var a = getClass().getDeclaredMethod("advance", int.class);
            // If either method is declared by a subclass (not this base), we consider Style A enabled
            cachedFA = (f.getDeclaringClass() != LLP.class) && (a.getDeclaringClass() != LLP.class);
        } catch (NoSuchMethodException e) {
            // If subclass doesn't declare one or both, fall back to ensure mode
            cachedFA = false;
        }
        return cachedFA;
    }
}
