package edu.utexas.ece.it;

import org.junit.jupiter.api.Test;

import edu.utexas.ece.llp.Boruvka;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class BoruvkaSimpleTest {

    @Test
    void mstTinyFourNodeGraph() {
        // Graph:
        // 0–1 (1)
        // 1–2 (2)
        // 2–3 (3)
        // 0–2 (4)
        // 1–3 (5)
        //
        // MST should be edges with weights 1, 2, 3 → total = 6
        List<Boruvka.Edge> edges = new ArrayList<>();
        edges.add(new Boruvka.Edge(0, 1, 1));
        edges.add(new Boruvka.Edge(1, 2, 2));
        edges.add(new Boruvka.Edge(2, 3, 3));
        edges.add(new Boruvka.Edge(0, 2, 4));
        edges.add(new Boruvka.Edge(1, 3, 5));

        Boruvka boruvka = new Boruvka(4, edges);

        // Use the LLP engine’s fixpoint loop
        boruvka.solve();

        List<Boruvka.Edge> mst = boruvka.getSolution();

        // Debug print (optional)
        int total = mst.stream().mapToInt(Boruvka.Edge::w).sum();
        System.out.println("MST edges:");
        for (Boruvka.Edge e : mst) {
            System.out.printf("  (%d,%d) w=%d%n", e.u(), e.v(), e.w());
        }
        System.out.println("Total weight = " + total);

        // Basic MST properties for this tiny connected graph
        assertEquals(3, mst.size(), "MST on 4 nodes must have 3 edges");
        assertEquals(6, total, "Expected total MST weight 6 (1+2+3)");
    }
}


/*
 * Expected Result: 
 * MST edges:
  (0,1) w=1
  (1,2) w=2
  (2,3) w=3
Total weight = 6
 */