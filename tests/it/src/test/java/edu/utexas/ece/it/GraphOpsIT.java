package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;
import edu.utexas.ece.graph.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphOpsIT {

    private static Graph emptyGraph(int n, boolean directed) {
        return new Graph() {
            public boolean isDirected() { return directed; }
            public int V() { return n; }
            public Iterable<Integer> out(int u) { return java.util.List.of(); }
            public Double weight(int u, int v) { return null; }
        };
    }

    @Test
    void shortestPath_returnsArraySizedToVertexCount() {
        Graph g = emptyGraph(5, true);
        double[] dist = GraphOps.shortestPath(g, 0);
        assertNotNull(dist, "distances array should not be null");
        assertEquals(g.V(), dist.length, "distances length should equal vertex count");
    }

    @Test
    void connectedComponents_returnsArraySizedToVertexCount() {
        Graph g = emptyGraph(7, false);
        int[] comps = GraphOps.connectedComponents(g);
        assertNotNull(comps, "components array should not be null");
        assertEquals(g.V(), comps.length, "components length should equal vertex count");
    }

    @Test
    void minimumSpanningTree_returnsNonNullArray() {
        Graph g = emptyGraph(4, false);
        Edge[] mst = GraphOps.minimumSpanningTree(g);
        assertNotNull(mst, "MST edge array should not be null");
        // Size is implementation-defined for stub; zero is fine for now.
    }
}
