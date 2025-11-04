package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.BellmanFord;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


public class BellmanFordTest {

    @Test
    void explainsAlgorithmAndValidatesOutput() {
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


/*
 * ==== Bellman–Ford LLP Example ====
    Graph Edges (u→v(weight)):
    0→1(4), 0→2(2), 1→2(1), 1→3(5), 2→3(-2)
    ----------------------------------
    Iteration 0 (initial): [0, INF, INF, INF]
    Iteration 1: relax 0→1(4), 0→2(2) => [0,4,2,INF]
    Iteration 2: relax 1→3(5), 2→3(-2) => [0,4,2,0]
    Final distances: [0, 4, 2, 0]
    ==================================

 */
