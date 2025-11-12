# Parallel Computing
Collection of multi-core and multi-process libraries, research, and exercises. 

## 1.0 Java LLP Library
#### Prerequisites
```
brew install gradle
brew install temurin
```

#### Example Usage
```java
BellmanFord bf = new BellmanFord(pre, w);
int[] dist = bf.getSolution();
```

#### Test
```bash 
./scripts/run_llp.sh
```

## 2.0 Research
Efficient algorithms for matrix mulitplications. 
See docs for more information 

#### Hybrid Bench
```
# Task-Level Parallelism (Coarse)
# Multiple independent matrices solved concurrently across processes
python3 scripts/hybrid_bench.py --mode manyA --n 256 --S 256 --outer 8

# Data-Level Parallelism (Fine, Single Thread)
# One matrix factorized once; multiple RHS solves with BLAS single-threaded
VECLIB_MAXIMUM_THREADS=1 python3 scripts/hybrid_bench.py --mode sameA --n 256 --S 256

# Data-Level Parallelism (Fine, 4 Threads)
# Same matrix workload but allow BLAS (Accelerate) to use 4 threads internally
VECLIB_MAXIMUM_THREADS=4 python3 scripts/hybrid_bench.py --mode sameA --n 256 --S 256

# On OpenBLAS systems (e.g., conda-forge environment):
# Replace VECLIB_MAXIMUM_THREADS with OPENBLAS_NUM_THREADS or OMP_NUM_THREADS
OPENBLAS_NUM_THREADS=4 python3 scripts/hybrid_bench.py --mode sameA --n 256 --S 256
```


