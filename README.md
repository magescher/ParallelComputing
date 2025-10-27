# Parallel Computing
Collection of multi-core and multi-process libraries and exercises. 

## LLP 

### Overview / Algorithm
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

### Assignment 
- Java Library/API that allows one to use LLP parallel algorithms to solve problems. 
- source
- program to generate testcases
- script that runs the program (tests below algorithms)

#### Low Level Design
Note: Work in progress
```uml
+ class LLP
    + G: # global state vector of reals initially ∀i : G [i] = 0;
    + B predicate
    + initGlobalState() 
    + getLeastFeasible(T: vector, B: predicate) // solver
```

#### Applications

##### Parallel Prefix 
- TODO 

##### Stable Marriage
- Input: ordered preferences of n men and n women
- Output: Man-optimal stable marriage

G[i]: index in the preference list for man i; initially 1 // top choice
Every man must be matched to a different woman and there must not be any blocking pair. For any man j, let
z = mpref[j][G[j]]; //current woman assigned to man j
¬∃i : ∃k ≤ G [i]: (z = mpref[i][k]) ∧ (rank[z][i] < rank[z][j]))

##### Shortest Path (no negative cycles)
- Algorithm: Bellman-Ford
- Input: a weighted directed graph and a source vertex
- Output: Least Cost of reaching any vertex i

- Input: pre(j): list of 1...n; w[i,j]: int for all i in pre(j) 
- Init: ( j == s ) ? G[j] = 0 : G[j] = maxint
- Ensure: G[j] <= min { G[i] + w[i,j] | i included in pre(j) }

##### Shortest path (w/ negative cycles)
- Algorithm: Johnson (finding min price vector)

w'[i,j] = w[i,j] + p[i] - p[j] // where prices >= 0 all w' >= 0
- Input: pre(j) : list of 1...n : w[i][j] for all i in pre(j)
- Init: p[j] = 0 for all j; w[i][j] for all i in pre(j)
- Ensure: p[j] >= max { p[i] - w[i,j] for i included inpre(j) }

##### Connected Components (undirected graph)
- Algorithm: Fast 

- Init: parent[j] = j
- Ensure 1: parent[j] = parent[ parent[j] ]
- Ensure 2: parent[j] >= max{parent[i] | for all (i,j) included in E}

##### Minimum Spanning Tree
- Algorithm: Boruvka
- TODO