package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.PrefixSum;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class ParPrefixSimpleTest {

    @Test
    public void testPrefixSumSimple() {
        int[] A = {1, 2, 3, 4};
        int[] S = {3, 6, 10}; // internal partials not used in leaves but for tree completeness
        PrefixSum ps = new PrefixSum(A, S);
        int[] result = ps.getSolution();
        System.out.println("Prefix sum Simple: " + Arrays.toString(result));
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
        int[] result = ps.getSolution();
        System.out.println("Prefix Sum Test negative numbers: " + Arrays.toString(result));
        assertArrayEquals(new int[]{2, 1, 4, 2}, result);
    }
}
