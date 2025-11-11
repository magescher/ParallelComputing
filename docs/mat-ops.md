# 0.0 Parllel Algorithms for Matrix Operations
Requirements and project outline for term paper on parallel algorithms for matrix operations.

Topic: Implement parallel algorithms to solve linear systems, invert matrices, compute determinants, and LU factorizations

**Task List**
- [...] Topic Research
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

# 1.0 Topic Research

## Idea 1: Parallel Structure of Linear Algebra Kernels in Quantitative Finance
- Scope: Parallelism within single matrix op
- Pros
    - Easy tie in to existing implementations (could build off LLP library even tho Java wouldn't be IRL best choice)
    - can tie to ahmeds (which we covered in class)

### Project Overview
This paper analyzes the parallel structure of key linear-algebra kernels (LU factorization, matrix inversion, and determinant computation) that form the backbone of quantitative-finance analytics such as covariance inversion and portfolio optimization. Rather than chasing raw performance, we examine how data dependencies, triangular update patterns, and synchronization costs bound achievable speedup. By comparing fine-grained and block-level parallel decompositions, we illustrate where concurrency emerges and where it collapses, connecting these structural limits to the design choices behind modern financial-computing libraries.

## Idea 2: Hybrid Parallelism for Financial Simulation Workloads
- Scope: Parallelism across many independent or semi-independent matrix ops
- Pros: 
    - Could leverage python or c/openMP
    - Applied, architectural, and industry aligned
    - More forward looking and impressive for interviews

### Project Overview
This paper explores hybrid parallelism that combines task-level concurrency across financial-simulation scenarios with data-level parallelism inside linear-algebra kernels such as LU factorization, inversion, and determinant computation. The approach mirrors production risk and covariance-analysis pipelines that run large ensembles of correlated matrix solves. Through simple nested-parallel implementations, we evaluate how coarse- and fine-grained parallelism interact, identifying practical trade-offs in throughput, latency, and resource utilization for scalable quantitative-finance systems.

# 2.0 Paper Outline
Title: X

## Abstract
- Finalize at end. Collection of thesis and topic statements. 
- Emphasize insights over raw performance (since matmul is well researched )

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
- psuedocode / diagrams

## Performance Results
- compare small vs medium matrices vs <large?> (e.g., 256×256, 512×512, ...) with 1 vs 4 vs 8 threads
- Plot speedup and discuss scalability limits
- Compare with benchmarks?

## Discussion / Industry Applications
- Relevance to covariance inversion, risk models
- Numerical stability, synchronization costs ?

## Conclusion
- summarize design lessons, link to quant applications