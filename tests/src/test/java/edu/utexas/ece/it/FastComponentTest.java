package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;
import java.util.*;
import edu.utexas.ece.llp.*;
import static org.junit.jupiter.api.Assertions.*;

public class FastComponentTest {
    @Test
    void test_components() {
        int[][] adj = {
            {1, 2}, {0, 2}, {0, 1}, {4}, {3}
        };
        ConnectedComponents fc = new ConnectedComponents(adj);
        int[] comps = fc.getSolution();

        // Expect two components: {0,1,2} and {3,4}
        int labelA = comps[0], labelB = comps[3];
        assertTrue(labelA != labelB, "disconnected sets should have different component labels");
    }

}