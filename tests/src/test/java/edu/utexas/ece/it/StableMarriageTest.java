package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.StableMatching;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class StableMarriageTest {

    @Test
    void tinyTwoByTwo() {
        int[][] mprefs = {
            {0, 1},  // m0 prefers w0 > w1
            {0, 1}   // m1 prefers w0 > w1
        };

        // w0: m0 > m1  => ranks [0, 1]
        // w1: m1 > m0  => ranks [1, 0]
        int[][] wprefs = {
            {0, 1},
            {1, 0}
        };

        StableMatching sm = new StableMatching(mprefs, wprefs);
        int[] wifeOfMan = sm.getSolution();

        assertArrayEquals(new int[]{0, 1}, wifeOfMan,
                "Expected m0→w0 and m1→w1, got " + Arrays.toString(wifeOfMan));
    }
}
