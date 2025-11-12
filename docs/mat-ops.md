# 0.0 Parllel Algorithms for Matrix Operations
Requirements and project outline for term paper on parallel algorithms for matrix operations.

Topic: Implement parallel algorithms to solve linear systems, invert matrices, compute determinants, and LU factorizations

**Task List**
- [x] Topic Research
- [ ] Outline
    - [ ] Section Skeleton
    - [ ] Introduction / Thesis
    - [ ] Topic Statements
- [ ] Algorithm / System Implementation
    - [ ] invert matrices
    - [ ] compute determinants
    - [ ] LU factorizations
- [ ] Benchmarking / Results / Discussion Points
- [ ] Content Draft
- [ ] Final Formatting Review
- [ ] Powerpoint Presentation 

**Requirements**
- 4 pages ~1500 words
- Double Spaced
- Leverage Template
- Sections/Topics: Introduction, project description, design alternatives, implementation, performance results, conclusion 

# 1.0 Topic Research

## 1.1 Idea: Parallel Structure of Linear Algebra Kernels in Quantitative Finance
- Scope: Parallelism within single matrix op
- Pros
    - Easy tie in to existing implementations (could build off LLP library even tho Java wouldn't be IRL best choice)
    - can tie to ahmeds (which we covered in class)

**Overview**  
This paper examines the parallel structure of linear-algebra kernels that form the backbone of quantitative-finance analytics such as covariance inversion and portfolio optimization. Matops are a famously and well researched topic so, rather than chasing performance, we observe how data dependencies, triangular update patterns, and synchronization costs bound achievable speedup. By comparing fine-grained and block-level parallel decompositions, we illustrate where concurrency emerges and where it collapses, thus <explaining> the design choices behind modern financial-computing libraries.

## 1.2 Idea: Hybrid Parallelism for Financial Simulation Workloads
- Scope: Parallelism across many independent or semi-independent matrix ops
- Pros: 
    - Could leverage python or c/openMP
    - Applied, architectural, and industry aligned
    - More forward looking and impressive for interviews

**Overview**  
This paper explores hybrid forms of parallelism in quantitative-finance systems that rely on parallel matrix operations. Matops are a well-studied and highly optimized area, so instead of trying to outperform existing libraries, we focus on how they are employed and scaled in real-life industry applications. In practice, large-scale risk and covariance analyses often run many simulation scenarios at once, each involving matrix factorizations or inversions. By evaulating hybrid course-grained and fine-grained parallelism strategies, we observe practical trade-offs where concurrency delivers real gains and where contention starts to erode. 

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