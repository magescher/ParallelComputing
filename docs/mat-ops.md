# 0.0 Parllel Algorithms for Matrix Operations
Requirements and project outline for term paper on parallel algorithms for matrix operations.

Topic: Implement parallel algorithms to solve linear systems, invert matrices, compute determinants, and LU factorizations

**Task List**
- [x] Topic Research
- [x] Written Content Outline
    - [X] Section Skeleton
    - [X] Introduction / Thesis
    - [X] Content Bullets
- [ ] System Implementation
    - [X] base command line interface and runner
    - [X] sameA module; fix. not properly testing fine grained 
    - [X] manyA module
    - [X] improve aesthetics of command line output
    - [X] script to automate runner for benchmarks and fixed simulation suite
    - [ ] make runner more robust to variable runtime environments
    - [ ] Run simulatiom suite on 2-3 diff runtime environments 
- [ ] Draft Written Content
    - [x] Introduction
    - [x] Design
    - [x] Implementation
    - [ ] Results
    - [ ] Discussion
    - [ ] Conclusion
    - [x] Abstract
- [ ] Finalize Written Content 
- [ ] Latex Translation and Final Formatting Review
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

#### 0.0 ABSTRACT
- TODO

#### 1.0 INTRODUCTION 
This paper explores hybrid forms of parallelism in quantitative-finance systems that rely on parallel matrix operations. Matrix operations are a well-studied and highly optimized area, so instead of trying to outperform existing libraries, we focus on how they are employed and scaled in real-life industry applications. In practice, large-scale risk and covariance analyses often run many simulation scenarios at once, each involving matrix factorizations or inversions. <..elaborate..> By evaluating hybrid course-grained and fine-grained parallelism strategies, we observe practical trade-offs where concurrency delivers real gains and where contention starts to erode.

#### 2.0 APPROACH AND METHODOLOGY
- Design Ethos - simulate real life applications while using abstractions where it makes sense; leverage simple interfaces to allow for ease of implementation while promoting future extensibility

#### 2.1 Design
- Python3 + SciPy to leverage BLAS instead of implementing LU from scratch ; focus on workload simulation instead of low level optimizations (tradeoff w/ C/OpenMP; CUDA/CPU alternatives). Better simulates development environment of quantitative researcher, and <…blah…> 
- High-level design of two modules (add figure?):
    - manyA: course-grained (task-level) parallelism, solving independent matrixes with multiple processes
    - sameA: fine-grained (kernel-level) parallelism, one factorization w/ many BLAS/RHS threads
- Command line runner to allow configurable input parameters (ease of implementation and promote future extendability)
- Matrix size(s) <default 256x256> and justifications <add/find references>
- Hardware considerations
- Assumptions/simplifications
####  2.2 Implementation
- Environment setup and BLAS configuration
- Parallel constructs: ProcessPollExecutor, multithreading , - <...>
- Pseudocode snippets for generating matrices, sameA, manyA, etc. 
- Computation and correctness check
- Benchmark measures
- Experimental conditions (CPU models/cores..). <This section here, at environment setup, or below?>

#### 3.0 EVALUATION & RESULTS
- Speedup curves (relative to <x?> baseline); include figure
- Key Observations:
    - manyA scales well up to outer=<x>, then oversubscribes CPU and throughput drops
    - sameA: diminishing returns ?
- ...

#### 4.0 DISCUSSION
- Observed tradeoffs + technical justification
- Where hybrid parallelization strategies pay off
- Connect to real-world financial workloads and applications (risk engines, … , etc.) <add references to industry, published data by large hedge funds>. How quant systems actually choose parallel strategies <reference>
- Takeaways that generalize beyond Python and tested experimental environment
… ... 

#### 5.0 CONCLUSIONS & FUTURE WORK
- Summary of findings (4.0)
- Implications / implied best practice
- Potential next steps / extensions

# 3.0 Example Runs
```
======================================================================
  Hybrid Parallelism Benchmark Suite
======================================================================
Benchmark script     : /Users/jaspurr/Documents/school/ParallelComputing/scripts/hybrid_bench.py
Detected CPU cores   : 8
Matrix dimension (n) : 256
RHS / scenarios (S)  : 256
OUTER sweep          : 1 2 4 8
INNER sweep          : 1 2 4 8
======================================================================


======================================================================
  Coarse-grained parallelism (task-level) — manyA
======================================================================
Description:
  - Many independent scenarios (matrices) solved in parallel.
  - Varying outer (number of worker processes), inner=1 BLAS thread.
  - Emulates Monte Carlo / stress tests / daily risk fan-out.

  mode       n       S  outer  inner         sec    throughput         resid

 manyA     256     256      1      1      0.7576        337.93     1.333e-15
 manyA     256     256      2      1      0.6000        426.68     1.333e-15
 manyA     256     256      4      1      0.5647        453.32     1.333e-15
 manyA     256     256      8      1      0.5913        432.95     1.333e-15

======================================================================
  Fine-grained parallelism (kernel-level) — sameA
======================================================================
Description:
  - Single shared matrix factorization reused across many RHS.
  - Single process (outer=1), varying inner (BLAS threads).
  - Emulates portfolio risk attribution / Greeks / factor models.

  mode       n       S  outer  inner         sec    throughput         resid

 sameA     256     256      1      1      0.0004     574688.16     2.305e-15
 sameA     256     256      1      2      0.0004     659297.62     2.305e-15
 sameA     256     256      1      4      0.0004     656199.88     2.305e-15
 sameA     256     256      1      8      0.0005     567680.32     2.305e-15

======================================================================
  Hybrid parallelism (task x kernel) — manyA
======================================================================
Description:
  - Combine task-level and kernel-level parallelism.
  - Outer = processes, inner = BLAS threads per process.
  - Skip configurations where outer x inner exceeds 8 cores.

  mode       n       S  outer  inner         sec    throughput         resid

 manyA     256     256      2      2      0.6574        389.42     1.333e-15
 manyA     256     256      2      4      0.6200        412.93     1.333e-15
# Skipping outer=2, inner=8 → total=16 > CORES=8
 manyA     256     256      4      2      0.5076        504.31     1.333e-15
# Skipping outer=4, inner=4 → total=16 > CORES=8
# Skipping outer=4, inner=8 → total=32 > CORES=8
# Skipping outer=8, inner=2 → total=16 > CORES=8
# Skipping outer=8, inner=4 → total=32 > CORES=8
# Skipping outer=8, inner=8 → total=64 > CORES=8

======================================================================
  All benchmarks complete
======================================================================
```

