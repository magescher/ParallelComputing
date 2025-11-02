package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;
import edu.utexas.ece.llp.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Integration test for LLP-based algorithms.
 */
class LLPAlgorithmsIT {

    @Test
    void bellmanFord_computesShortestPathsCorrectly() {
        /*
         * Graph:
         * 0 → 1 (4)
         * 0 → 2 (2)
         * 1 → 2 (1)
         * 1 → 3 (5)
         * 2 → 3 (-2)
         */
        int n = 4;

        int[][] pre = {
            {},        // 0
            {0},       // 1
            {0, 1},    // 2
            {1, 2}     // 3
        };

        int INF = 9999;
        int[][] w = {
            {0, 4, 2, INF},
            {INF, 0, 1, 5},
            {INF, INF, 0, -2},
            {INF, INF, INF, 0}
        };

        // Instantiate and run the LLP-based Bellman-Ford
        BellmanFord bf = new BellmanFord(n, pre, w);
        bf.solve();
        int[] dist = bf.getSolution();

        // Expected shortest-path distances from node 0
        int[] expected = {0, 4, 3, 1};

        assertArrayEquals(expected, dist,
            "Bellman–Ford should compute correct shortest-path distances");
    }

    @Test
    void fastComponents_returnsLabelsForAllVertices() {
        int n = 5;
        int[][] adj = {
            {1, 2}, {0, 2}, {0, 1}, {4}, {3}
        };
        FastComponents fc = new FastComponents(n, adj);
        fc.solve();
        int[] comps = fc.getSolution();

        assertEquals(n, comps.length, "component array should cover all vertices");

        // Expect two components: {0,1,2} and {3,4}
        int labelA = comps[0], labelB = comps[3];
        assertTrue(labelA != labelB, "disconnected sets should have different component labels");
    }
}
