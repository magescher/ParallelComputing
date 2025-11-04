package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.BellmanFord;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class BellmanFordTest {

    @Test
    void tinyThreeNodeCase() {
        int num = 3;

        // predecessors of each vertex j
        // pre[0] = {}      (source)
        // pre[1] = {0}
        // pre[2] = {0,1}
        int[][] pre = new int[][]{
            {},      // 0
            {0},     // 1
            {0,1}    // 2
        };

        // weights w[i][j]  (only the used entries matter)
        int[][] w = new int[num][num];
        w[0][1] = 5;
        w[1][2] = 2;
        w[0][2] = 10;

        BellmanFord bf = new BellmanFord(pre, w);
        int[] dist = bf.getSolution();
        assertArrayEquals(new int[]{0, 5, 7}, dist,
                "Expected distances [0,5,7] but got " + Arrays.toString(dist));
    }
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
        BellmanFord bf = new BellmanFord(pre, w);
        int[] dist = bf.getSolution();

        // Expected shortest-path distances from node 0
        int[] expected = {0, 4, 2, 0};

        System.out.println("dist = " + java.util.Arrays.toString(dist));
        assertArrayEquals(expected, dist,
            "Bellman–Ford should compute correct shortest-path distances");
    }
}
