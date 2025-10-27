package edu.utexas.ece.llp;

/**
 * Basic interface representing a vector used as the global state in LLP.
 */
public interface Vector<T> {
    T get(int i);
    void set(int i, T value);
    int size();
}
