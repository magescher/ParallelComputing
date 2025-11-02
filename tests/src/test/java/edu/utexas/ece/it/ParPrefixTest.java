package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.ParPrefix;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParPrefixSimpleTest {

    @Test
    void tinyTwoElementCase() {
        // A has 2 elements; S is unused for n=2 but must exist (can be length 0 or any).
        int[] A = {5, 99};
        int[] S = {}; // not used by the algorithm in this tiny case

        ParPrefix pp = new ParPrefix(A, S);

        pp.solve();

        int[] out = pp.getSolution();
        assertArrayEquals(new int[]{0, 5}, out,
                "Expected [0, 5] but got " + Arrays.toString(out));
    }
}
