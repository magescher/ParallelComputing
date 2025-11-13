#!/usr/bin/env bash
# ---------------------------------------------------------------------
# Hybrid parallelism benchmark driver
# - Coarse-grained (task-level) sweeps
# - Fine-grained (kernel-level) sweeps
# - Hybrid (outer × inner) grid under a core budget
# ---------------------------------------------------------------------

set -euo pipefail

# Resolve path to the benchmark Python script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BENCH="${SCRIPT_DIR}/hybrid_bench.py"

# ---------------------------------------------------------------------
# Global configuration (edit as needed)
# ---------------------------------------------------------------------
N=256          # matrix dimension
S=256          # number of scenarios / RHS
OUTER_SWEEP=(1 2 4 8)
INNER_SWEEP=(1 2 4 8)

# Detect core count for hybrid oversubscription checks
CORES="$(
  python3 - <<'PY'
import os
print(os.cpu_count() or 1)
PY
)"

# ---------------------------------------------------------------------
# Pretty printing helpers
# ---------------------------------------------------------------------
hr() {
  printf '%*s\n' 62 '' | tr ' ' '='
}

section() {
  echo
  hr
  printf "  %s\n" "$1"
  hr
}

subsection() {
  echo
  echo "---- $1 ----"
}

cmd() {
  echo "  $*"
  "$@"
}

# ---------------------------------------------------------------------
# Workload runners
# ---------------------------------------------------------------------

run_coarse_sweep() {
  section "Coarse-grained parallelism (task-level) — manyA"

  echo "Description:"
  echo "  - Many independent scenarios (matrices) solved in parallel."
  echo "  - Varying outer (number of worker processes), inner=1 BLAS thread."
  echo "  - This emulates Monte Carlo / stress tests / daily risk fan-out."
  echo

  for o in "${OUTER_SWEEP[@]}"; do
    subsection "manyA: n=${N}, S=${S}, outer=${o}, inner=1"
    cmd python3 "${BENCH}" \
      --mode manyA \
      --n "${N}" \
      --S "${S}" \
      --outer "${o}" \
      --inner 1
    echo
  done
}

run_fine_sweep() {
  section "Fine-grained parallelism (kernel-level) — sameA"

  echo "Description:"
  echo "  - Single shared matrix factorization reused across many RHS."
  echo "  - Single process (outer=1), varying inner (BLAS threads)."
  echo "  - This emulates portfolio risk attribution / Greeks / factor models."
  echo

  for i in "${INNER_SWEEP[@]}"; do
    subsection "sameA: n=${N}, S=${S}, outer=1, inner=${i}"
    cmd python3 "${BENCH}" \
      --mode sameA \
      --n "${N}" \
      --S "${S}" \
      --outer 1 \
      --inner "${i}"
    echo
  done
}

run_hybrid_grid() {
  section "Hybrid parallelism (task × kernel) — manyA"

  echo "Description:"
  echo "  - Combine task-level and kernel-level parallelism."
  echo "  - Outer = processes, inner = BLAS threads per process."
  echo "  - Skip configurations where outer × inner exceeds ${CORES} cores."
  echo

  for o in "${OUTER_SWEEP[@]}"; do
    for i in "${INNER_SWEEP[@]}"; do
      total=$(( o * i ))
      if (( total > CORES )); then
        echo "Skipping (outer=${o}, inner=${i}) → total threads=${total} > CORES=${CORES}"
        continue
      fi

      subsection "HYBRID manyA: n=${N}, S=${S}, outer=${o}, inner=${i} (total threads ≈ ${total})"
      cmd python3 "${BENCH}" \
        --mode manyA \
        --n "${N}" \
        --S "${S}" \
        --outer "${o}" \
        --inner "${i}"
      echo
    done
  done
}

# ---------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------

section "Hybrid Parallelism Benchmark Suite"
echo "Benchmark script     : ${BENCH}"
echo "Detected CPU cores   : ${CORES}"
echo "Matrix dimension (n) : ${N}"
echo "RHS / scenarios (S)  : ${S}"
echo "OUTER sweep          : ${OUTER_SWEEP[*]}"
echo "INNER sweep          : ${INNER_SWEEP[*]}"
hr
echo

run_coarse_sweep
run_fine_sweep
run_hybrid_grid

section "All benchmarks complete"
