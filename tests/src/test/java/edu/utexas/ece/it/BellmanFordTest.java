package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.BellmanFord;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class BellmanFordSimpleTest {

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
}
