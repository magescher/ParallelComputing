package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.Johnson;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

/**
 * Simple test for the Johnson LLP variant.
 *
 * Graph:
 *   0 → 1 (2)
 *   0 → 2 (5)
 *   1 → 2 (1)
 */
class JohnsonTest {

    @Test
    void shortest_path() {
        int[][] pre = {
            {},      // 0
            {0},     // 1
            {0, 1}   // 2
        };
        int[][] w = new int[3][3];
        w[0][1] = -2; // negative
        w[1][2] = -3; // negative
        w[0][2] =  1;

        Johnson j = new Johnson(pre, w);
        int[] G = j.getSolution();

        // This instance converges to:
        assertArrayEquals(new int[]{0, 2, 5}, G);

    }
}


/*
 * Expected Output: Johnson potentials: [0, 2, 5]
 */