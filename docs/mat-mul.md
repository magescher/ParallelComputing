# Parllel Algorithms for Matrix Operations
Requirements and project outline for term paper on parallel algorithms for matrix operations.

Topic: Implement parallel algorithms to solve linear systems, invert matrices, compute determinants, and LU factorizations

**Task List**
- [...] Structure Topic
- [ ] Outline
    - [ ] Section Skeleton
    - [ ] Introduction / Thesis
    - [ ] Topic Statements
- [ ] Algorithm Implementation
    - [ ] solve linear systems
    - [ ] invert matrices
    - [ ] compute determinants
    - [ ] LU factorizations
- [ ] Algorithm Benchmarking
- [ ] Content Draft
- [ ] Final Formatting Review
- [ ] Powerpoint Presentation 

**Requirements**
- 4 pages ~1500 words
- Double Spaced
- Leverage Template
- Sections/Topics: Introduction, project description, design alternatives, implementation, performance results, conclusion 

# Paper Outline
Title: Parallel Linear Algebra Kernels for Quantitative Finance: LU Factorization, Inversion, and Determinant Computation

## Abstract
- Finalize at end. Collection of thesis and topic statements. 
- project investigates parallelization strategies for LU factorization and its applications (solving Ax=b, computing determinants, inverses) in financial contexts (e.g., covariance estimation)
- Emphasize insights over raw performance (since these topics are well researched )

## Introduction
- Motivate with quant finance examples; state why linear systems are core
- Mention goal: design alternatives and performance trade-offs
- Thesis statement

## Project Description / Overview

## Design Alternatives
- Extend LLP vs various...
- ...

## Implementation
- describe platform (e.g., java vs cuda vs python multiprocessing, extend existing LLP libary, etc)
- extend existing LLP library?
- Show pseudocode and a dependency DAG for LU

## Performance Results
- compare small vs medium matrices vs <large?> (e.g., 256×256, 512×512, ...) with 1 vs 4 vs 8 threads
- Plot speedup and discuss scalability limits
- Compare with benchmarks?

## Discussion / Industry Applications
- Relevance to covariance inversion, risk models
- Numerical stability, synchronization costs ?

## Conclusion
- summarize design lessons, link to quant applications