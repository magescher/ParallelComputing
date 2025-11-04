package edu.utexas.ece.llp;

import org.junit.jupiter.api.Test;
import java.util.*;
import edu.utexas.ece.llp.*;
import static org.junit.jupiter.api.Assertions.*;

public class MST {

  private static List<Boruvka.Edge> edges(int... t) {
    List<Boruvka.Edge> e = new ArrayList<>();
    for (int i = 0; i + 2 < t.length; i += 3)
      e.add(new Boruvka.Edge(t[i], t[i + 1], t[i + 2]));
    return e;
  }

  private static int weight(List<Boruvka.Edge> T) {
    int s = 0;
    for (var e : T) s += e.w();
    return s;
  }

  private static void assertTree(int n, List<Boruvka.Edge> T) {
    assertEquals(n - 1, T.size(), "Wrong number of edges");
    int[] p = new int[n];
    for (int i = 0; i < n; i++) p[i] = i;
    for (var e : T) {
      int a = find(p, e.u()), b = find(p, e.v());
      assertNotEquals(a, b, "Cycle detected");
      p[b] = a;
    }
  }

  private static int find(int[] p, int x) {
    while (p[x] != x) x = p[x] = p[p[x]];
    return x;
  }

  // ---------- MST tests ----------

  @Test
  public void triangle_prefers_lightest_two() {
    var mst = new Boruvka(3, edges(0,1,1, 1,2,2, 0,2,10)).getSolution();
    assertTree(3, mst);
    assertEquals(3, weight(mst));
  }

  @Test
  public void line_graph_is_itself_mst() {
    var mst = new Boruvka(4, edges(0,1,5, 1,2,1, 2,3,4)).getSolution();
    assertTree(4, mst);
    assertEquals(10, weight(mst));
  }

  @Test
  public void square_picks_three_unit_edges() {
    var mst = new Boruvka(4, edges(
      0,1,1, 1,2,1, 2,3,1, 3,0,1,
      0,2,5, 1,3,5)).getSolution();
    assertTree(4, mst);
    assertEquals(3, weight(mst));
  }

  @Test
  public void star_graph_selects_all_spokes() {
    var mst = new Boruvka(5, edges(
      0,1,2, 0,2,2, 0,3,2, 0,4,2,
      1,2,10, 2,3,10, 3,4,10)).getSolution();
    assertTree(5, mst);
    assertEquals(8, weight(mst));
  }

  @Test
  public void equal_weights_still_valid() {
    var mst = new Boruvka(4, edges(
      0,1,1, 1,2,1, 2,3,1, 0,3,1, 1,3,1)).getSolution();
    assertTree(4, mst);
    assertEquals(3, weight(mst));
  }

  @Test
  public void single_vertex_empty() {
    var mst = new Boruvka(1, Collections.emptyList()).getSolution();
    assertTrue(mst.isEmpty());
  }

  // ---------- MSF (disconnected) ----------

  @Test
  public void disconnected_graph_returns_msf() {
    var msf = new Boruvka(6, edges(
      0,1,1, 1,2,2, 0,2,3,   // comp A → 1+2=3
      3,4,2, 4,5,2, 3,5,10   // comp B → 2+2=4
    )).getSolution();
    assertEquals(4, msf.size());
    assertEquals(7, weight(msf)); // 3 + 4
  }

  @Test
  public void isolated_vertex_is_ignored() {
    var msf = new Boruvka(4, edges(0,1,1, 1,2,1)).getSolution();
    assertEquals(2, msf.size());
    assertEquals(2, weight(msf));
  }
}

