package edu.utexas.ece.graph;

import java.util.*;

/**
 * Basic graph interface for LLP-based algorithms.
 * Supports directed or undirected graphs, weighted edges,
 * and adjacency iteration.
 */
public interface Graph {

    /** @return true if the graph is directed */
    boolean isDirected();

    /** @return number of vertices in the graph */
    int V();

    /**
     * Returns all outgoing neighbors of vertex u
     * @param u source vertex
     * @return iterable of neighbor vertex indices
     */
    Iterable<Integer> out(int u);

    /**
     * Returns the edge weight from u to u, or null if no edge exists.
     */
    Double weight(int u, int v);
}
