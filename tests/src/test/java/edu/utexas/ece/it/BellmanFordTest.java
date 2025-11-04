package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.BellmanFord;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


public class BellmanFordTest {

    
    @Test
    void shortest_path() {
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
    void shortest_path2() {
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

    @Test
    void explain_algo_validate() {
        int[][] pre = {
            {},        // 0: no predecessors (source)
            {0},       // 1: reachable from 0
            {0, 1},    // 2: reachable from 0 or 1
            {1, 2}     // 3: reachable from 1 or 2
        };

        int INF = 9999;
        int[][] w = {
            {0, 4, 2, INF},   // 0→1(4), 0→2(2)
            {INF, 0, 1, 5},   // 1→2(1), 1→3(5)
            {INF, INF, 0, -2},// 2→3(-2)
            {INF, INF, INF, 0}
        };

        // Construct and solve
        BellmanFord bf = new BellmanFord(pre, w);
        bf.solve();
        int[] dist = bf.getSolution();

        // --- Explain the steps for clarity ---

        System.out.println("==== Bellman–Ford LLP Example ====");
        System.out.println("Graph Edges (u→v(weight)):");
        System.out.println("0→1(4), 0→2(2), 1→2(1), 1→3(5), 2→3(-2)");
        System.out.println("----------------------------------");

        // Expected progression (conceptually):
        System.out.println("Iteration 0 (initial): [0, INF, INF, INF]");
        System.out.println("Iteration 1: relax 0→1(4), 0→2(2) => [0,4,2,INF]");
        System.out.println("Iteration 2: relax 1→3(5), 2→3(-2) => [0,4,2,0]");
        System.out.println("Final distances: " + Arrays.toString(dist));
        System.out.println("==================================");

        // --- Verify correctness ---
        int[] expected = {0, 4, 2, 0};
        assertArrayEquals(expected, dist,
                "Expected shortest-path distances [0,4,2,0]");
    }
}