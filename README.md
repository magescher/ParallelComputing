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
- See [docs](./docs/planning/mat-ops.md) for project planning and log 
- See [reports](./docs/reports/parallel_matrix_ops/) for LaTex report

#### Hybrid Bench
```bash
./scripts/run_hybrid_bench.sh
```


