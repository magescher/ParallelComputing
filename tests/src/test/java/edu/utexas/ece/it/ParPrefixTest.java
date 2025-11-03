package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.PrefixSum;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParPrefixSimpleTest {

    @Test
    void tinyTwoElementCase() {
        // A has 2 elements; S is unused for n=2 but must exist (can be length 0 or any).
        int[] A = {5, 99};
        int[] S = {}; // not used by the algorithm in this tiny case

        PrefixSum pp = new PrefixSum(A, S);
        int[] out = pp.getSolution();
        assertArrayEquals(new int[]{0, 5}, out,
                "Expected [0, 5] but got " + Arrays.toString(out));
    }

    @Test
    public void testPrefixSumSimple() {
        int[] A = {1, 2, 3, 4};
        int[] S = {3, 6, 10}; // internal partials not used in leaves but for tree completeness

        PrefixSum ps = new PrefixSum(A, S);
        int[] result = ps.getSolution();

        System.out.println("Prefix sums: " + Arrays.toString(result));

        assertArrayEquals(new int[]{1, 3, 6, 10}, result);
    }

    @Test
    public void testAllZeros() {
        int[] A = {0, 0, 0, 0};
        int[] S = {0, 0, 0};
        PrefixSum ps = new PrefixSum(A, S);
        assertArrayEquals(new int[]{0, 0, 0, 0}, ps.getSolution());
    }

    @Test
    public void testNegativeNumbers() {
        int[] A = {2, -1, 3, -2};
        int[] S = {1, 2, 3};
        PrefixSum ps = new PrefixSum(A, S);
        assertArrayEquals(new int[]{2, 1, 4, 2}, ps.getSolution());
    }
}
