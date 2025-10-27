package edu.utexas.ece.graph;

import edu.utexas.ece.llp.*;

/**
 * Static entry points for LLP-based graph algorithms.
 * Each method will later construct an LLP module and run it through LLPSolver.
 */
public final class GraphOps {

    private GraphOps() { } // utility class

    /**
     * Computes shortest-path distances from a source vertex using
     * a lattice-linear parallel algorithm (e.g., Bellman-Ford or reweighted).
     */
    public static double[] shortestPath(Graph g, int s) {
        // TODO: build module + invoke LLPParallelSolver
        return new double[g.V()];
    }

    /**
     * Finds connected components in an undirected graph
     * using an LLP-based parallel algorithm.
     */
    public static int[] connectedComponents(Graph g) {
        // TODO: build module + invoke LLPParallelSolver
        return new int[g.V()];
    }

    /**
     * Computes the Minimum Spanning Tree using Boruvka’s LLP algorithm
     */
    public static Edge[] minimumSpanningTree(Graph g) {
        // TODO: build module + invoke LLPSolver
        return new Edge[0];
    }
}
