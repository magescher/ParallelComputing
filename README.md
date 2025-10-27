# Parallel Computing
Collection of multi-core and multi-process libraries and exercises. 

## LLP 
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


- Solve:
From notes:
```python
def get_least_feasible(T: vector, B: predicate)
    T; # top element of the lattice
    G; # vector of reals initially ∀i : G [i] = 0;
    while ∃j: forbidden(G, j, B) do
        for all j such that forbidden(G, j, B) in parallel:
            if (α(G, j, B) > T[j]): return None 
            else G[j] := α(G , j, B)
    return G ; # the optimal solution
```

## Lattice Linear Predicate
Java Library/API that allows one to use LLP parallel algorithms to solve problems. 
- source
- program to generate testcases
- script that runs the program (tests below algorithms)

UML 
Note: re
+ class LLP
    + globalState: # vector of reals initially ∀i : G [i] = 0;
    + B predicate

    + getLeastFeasible(T: vector, B: predicate)



#### Stable Marriage Problem
Input: ordered preferences of n men and n women
Output: Man-optimal stable marriage

G[i]: index in the preference list for man i; initially 1 // top choice
Every man must be matched to a different woman and there must not be any blocking pair. For any man j, let
z = mpref[j][G[j]]; //current woman assigned to man j
¬∃i : ∃k ≤ G [i]: (z = mpref[i][k]) ∧ (rank[z][i] < rank[z][j]))

#### Parallel Prefix problem

#### Finding connected components of an undirected graph (Fast Algorithm)

#### Bellman-Ford Algorithm (no negative cycles)
Input: a weighted directed graph and a source vertex
Output: Least Cost of reaching any vertex i

#### Johnson’s algorithm for shortest path

#### Boruvka’s Algorithm