### 1.1 Notes
- Step 1: 
    - Model the underlying search space - a distributive lattice of state vectors. 
        - Global State Vector where G [i] is the component for process i.
        - The choice for a single process is total ordered
    - The order on the lattice is based on the optimization objective of the problem.
- Step 2: 
    - Define the feasibility predicate B. An element is feasible if it satisfies constraints of the problem
    - Forbidden State?
- Step 3: 
    - Check whether the feasibility predicate B is Lattice-Linear
    - If B is lattice-linear, LLP Algorithm will return the optimal feasible solution.

- Finding an element in the lattice that sarifies the given predicate B is called predicate detection problem. 
- Find the minimum element thtat satisfies B (whenever it exists) is the combinatorial optimization problem
- Lattice linearity enables the efficient computtation of this minimum ellement
- A key element in the development of an efficient predicate detection algirthm is that of forbidden state: something being false in G implies G contains a forbidden state 

3 Sections:
+ init 
+ always 
    + defines addition variables derived from G; can be seen as macros
+ Predicate: 
    + forbidden & advance
    + ensure //use when expression in a monotonic function of G

- Solve:
From notes:
```python
def get_least_feasible(T: vector, B: predicate)
    T; # top element of the lattice
    G; # vector of reals initially ∀i : G [i] = 0;
    while ∃j: forbidden(G, j, B) do
        for all j such that forbidden(G, j, B) in parallel:
            if α(G, j, B) > T[j]: return None 
            else G[j] := α(G , j, B)
    return G ; # the optimal solution
```

### 1.2 Assignment 
- Java Library/API that allows one to use LLP parallel algorithms to solve problems. 
- source
- program to generate testcases
- script that runs the program (tests below algorithms)

#### 1.2.1 Low Level Design
- Note: Work in progress

```uml
+ interface LLP<T>
    + G(): Vector<T>                                    // global state vector 
    + T(): Vector<T>                                    // top elem of lattice         
    + init(): void                                      // initialize global state G
    + always(): void                                    // recompute derived variables/macros 
    + isForbidden(j: int): boolean
    + advance(j: int): T
    + ensure(): <?>                                     // ? 

+ class Graph
    + isDirected: boolean
    + V(): int
    + out(u: int): Iterable<int>
    + weight(u: int, v: int): double?                   // null if no edge
    + addEdge(u: int, v: int, w: double = 1.0): void

+ class GraphOps
    + shortestPath(g: Graph, s: int): double[]          // Bellman-Ford (LLP) or Johnson (LLP)
    + connectedComponents(g: Graph): int[]              // Fast CC (LLP)
    + minimumSpanningTree(g: Graph): Edge[]             // Boruka (LLP)

```

#### 1.2.2 Applications

##### Parallel Prefix 
- TODO 

##### Stable Marriage
- Input: ordered preferences of n men and n women
- Output: Man-optimal stable marriage

- Note: Pj: Code for thread j
- Input: mpref[i,k]: int for all i,k; rank[k][i]: int for all k,i; I: array[1...n][1...n]: int // init vector
- Init: G[j] = i[j] // works for any init 
- Always: z = mpref[ j ][ G[j] ]
- Forbidden: ∃i : ∃k ≤ G [i]: (z = mpref[i][k]) ∧ (rank[z][i] < rank[z][j]))
    - Advance: G[j] = G[j] + 1

##### Shortest Path (no negative cycles)
- Algorithm: Bellman-Ford
- Input: a weighted directed graph and a source vertex
- Output: Least Cost of reaching any vertex i

- Input: pre(j): list of 1...n; w[i,j]: int for all i in pre(j) 
- Init: ( j == s ) ? G[j] = 0 : G[j] = maxint
- Ensure: G[j] <= min { G[i] + w[i,j] | i included in pre(j) }

##### Shortest path (w/ negative cycles)
- Algorithm: Johnson (finding min price vector)

- w'[i,j] = w[i,j] + p[i] - p[j] // where prices >= 0 all w' >= 0
- Input: pre(j) : list of 1...n : w[i][j] for all i in pre(j)
- Init: p[j] = 0 for all j; w[i][j] for all i in pre(j)
- Ensure: p[j] >= max { p[i] - w[i,j] for i included inpre(j) }

##### Connected Components (undirected graph)
- Algorithm: Fast 

- Init: parent[j] = j
- Ensure 1: parent[j] = parent[ parent[j] ]
- Ensure 2: parent[j] >= max{ parent[i] | for all (i,j) included in E }

##### Minimum Spanning Tree
- Algorithm: Boruvka

- TODO