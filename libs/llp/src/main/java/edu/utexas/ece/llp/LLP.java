package edu.utexas.ece.llp;

/**
 * Interface representing a lattice-linear problem module.
 * Encapsulates the global state and problem-specific update logic.
 */
public interface LLP<T> {

    /**
     * Global state vector (mutable).
     */
    Vector<T> G();

    /**
     * Top element of lattice (optional termination guard).
     */
    Vector<T> T();

    /**
     * Initializes the global state vector.
     */
    void init();

    /**
     * Recomputes any derived variables or macros (optional).
     */
    void always();

    /**
     * Returns true if component j is currently forbidden.
     */
    boolean isForbidden(int j);

    /**
     * Computes the least monotone fix (G, j) for component j.
     */
    T advance(int j);

    /**
     * Returns true if the module is in a feasible state.
     * Default implementation returns false.
     */
    default boolean isFeasible() {
        return false;
    }
}
