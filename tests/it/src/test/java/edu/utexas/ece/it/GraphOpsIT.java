package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;
import edu.utexas.ece.graph.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test placeholder for GraphOps.
 */
class GraphOpsIT {

    @Test
    void testShortestPathStub() {
        Graph g = new Graph() {
            public boolean isDirected() { return true; }
            public int V() { return 4; }
            public Iterable<Integer> out(int u) { return java.util.List.of(); }
            public Double weight(int u, int v) { return null; }
        };

        double[] result = GraphOps.shortestPath(g, 0);
        assertEquals(4, result.length, "Result vector should match vertex count");
    }
}
