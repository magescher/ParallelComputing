#!/usr/bin/env python3
import os, time, argparse, multiprocessing as mp

def set_blas_threads(n: int):
    for v in ("MKL_NUM_THREADS","OPENBLAS_NUM_THREADS","OMP_NUM_THREADS",
              "NUMEXPR_NUM_THREADS","VECLIB_MAXIMUM_THREADS"):
        os.environ[v] = str(n)
    os.environ.setdefault("ACCELERATE_DISABLE_FAST_MATH", "1")

# Cap BLAS threads before importing workers / starting pools; Placement before main important
set_blas_threads(1) 

from concurrent.futures import ProcessPoolExecutor
import numpy as np
from scipy.linalg import lu_factor, lu_solve
from threadpoolctl import threadpool_limits
import faulthandler; faulthandler.enable()


# ---------------------------------------------------------------------
# Core workloads
# ---------------------------------------------------------------------

def spd(n: int, lam=1e-3, seed=0):
    rng = np.random.default_rng(seed)
    X = rng.standard_normal((n, n))
    return X.T @ X + lam*np.eye(n)

def scenario_manyA(n: int, seed: int, inner:int):
    # Inner math limited to inner BLAS threads via threadpool_limits
    with threadpool_limits(limits=inner, user_api="blas"): # Note: May need to ammend to support variable environments (openmp etc)
        A = spd(n, seed)
        b = np.ones(n)
        lu, piv = lu_factor(A)
        x = lu_solve((lu, piv), b)
        return float(np.linalg.norm(A @ x - b) / np.linalg.norm(b))

def bench_manyA(n: int, S: int, inner: int, outer: int) -> dict:
    ctx = mp.get_context("spawn")                
    t0 = time.perf_counter()
    with ProcessPoolExecutor(max_workers=outer, mp_context=ctx) as ex:
        resids = list(ex.map(scenario_manyA, [n]*S, range(S), [inner]*S))
    dt = time.perf_counter() - t0
    return {"workload":"manyA","n":n,"S":S,"outer":outer,"inner":inner,
            "throughput": S/dt, "sec": dt, "median_resid": float(np.median(resids))}

def bench_sameA(n: int, S: int, inner: int) -> dict:
    # fine-grained: single process, BLAS threads = inner
    with threadpool_limits(limits=inner, user_api="blas"): # Note: May need to ammend to support variable environments (openmp etc)
        A = spd(n, 42)
        lu, piv = lu_factor(A)
        B = np.ones((n, S))
        t0 = time.perf_counter()
        X = lu_solve((lu, piv), B)
        dt = time.perf_counter() - t0
        resid = np.linalg.norm(A @ X - B) / np.linalg.norm(B)
    return { "workload": "sameA", "n": n, "S": S, "outer": 1, "inner": inner,
            "throughput": S/dt, "sec": dt, "median_resid": float(resid) }


# ---------------------------------------------------------------------
# Pretty-print helpers
# ---------------------------------------------------------------------

def format_header() -> str:
    return (
        f"{'mode':>6}  {'n':>6}  {'S':>6}  "
        f"{'outer':>5}  {'inner':>5}  "
        f"{'sec':>10}  {'throughput':>12}  {'resid':>12}"
    )

def format_result(res: dict) -> str:
    return (
        f"{res['workload']:>6}  "
        f"{res['n']:6d}  {res['S']:6d}  "
        f"{res['outer']:5d}  {res['inner']:5d}  "
        f"{res['sec']:10.4f}  {res['throughput']:12.2f}  {res['median_resid']:12.3e}"
    )

# ---------------------------------------------------------------------
# CLI/main
# ---------------------------------------------------------------------
def main():
    p = argparse.ArgumentParser()
    p.add_argument("--n", type=int, default=256)
    p.add_argument("--S", type=int, default=256)
    p.add_argument("--outer", type=int, default=os.cpu_count() or 4,
        help="task-level parallelism (number of processes)")
    p.add_argument("--inner", type=int, default=1,
        help="kernel-level parallelism (BLAS threads per process)")
    p.add_argument("--mode", choices=["manyA","sameA"], default="manyA")  
    p.add_argument("--pretty", action="store_true",
        help="print a single formatted table row instead of a raw dict")
    p.add_argument("--print-header", action="store_true",
        help="print a table header and exit")
    args = p.parse_args()

    try:
        mp.set_start_method("spawn")
    except RuntimeError:
        pass

    # If we only want the header, don't run the benchmark at all
    if args.print_header:
        print(format_header())
        return

    if args.mode == "manyA":
        result = bench_manyA(args.n, args.S, args.inner, args.outer)
    else:
        result = bench_sameA(args.n, args.S, args.inner)

    if args.pretty:
        print(format_result(result))
    else:
        print(result)

if __name__ == "__main__":
    main()
