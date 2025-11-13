#!/usr/bin/env bash
# ---------------------------------------------------------------------
# Hybrid parallelism benchmark driver
# - Coarse-grained (task-level) sweeps
# - Fine-grained (kernel-level) sweeps
# - Hybrid (outer x inner) grid under a core budget
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
  printf '%*s\n' 70 '' | tr ' ' '='
}

section() {
  echo
  hr
  printf "  %s\n" "$1"
  hr
}

# ---------------------------------------------------------------------
# Workload runners
# ---------------------------------------------------------------------

run_coarse_sweep() {
  section "Coarse-grained parallelism (task-level) — manyA"

  cat <<EOF
Description:
  - Many independent scenarios (matrices) solved in parallel.
  - Varying outer (number of worker processes), inner=1 BLAS thread.
  - Emulates Monte Carlo / stress tests / daily risk fan-out.

EOF

  # Print table header once
  python3 "${BENCH}" --print-header
  echo

  for o in "${OUTER_SWEEP[@]}"; do
    python3 "${BENCH}" \
      --mode manyA \
      --n "${N}" \
      --S "${S}" \
      --outer "${o}" \
      --inner 1 \
      --pretty
  done
}

run_fine_sweep() {
  section "Fine-grained parallelism (kernel-level) — sameA"

  cat <<EOF
Description:
  - Single shared matrix factorization reused across many RHS.
  - Single process (outer=1), varying inner (BLAS threads).
  - Emulates portfolio risk attribution / Greeks / factor models.

EOF

  python3 "${BENCH}" --print-header
  echo

  for i in "${INNER_SWEEP[@]}"; do
    python3 "${BENCH}" \
      --mode sameA \
      --n "${N}" \
      --S "${S}" \
      --outer 1 \
      --inner "${i}" \
      --pretty
  done
}

run_hybrid_grid() {
  section "Hybrid parallelism (task x kernel) — manyA"

  cat <<EOF
Description:
  - Combine task-level and kernel-level parallelism.
  - Outer = processes, inner = BLAS threads per process.
  - Skip configurations where outer x inner exceeds ${CORES} cores.

EOF

  python3 "${BENCH}" --print-header
  echo

  for o in "${OUTER_SWEEP[@]}"; do
    for i in "${INNER_SWEEP[@]}"; do
      # Skip cases where outer==1 or inner==1
      if (( o == 1 )) || (( i == 1 )); then
        continue
      fi
      total=$(( o * i ))
      if (( total > CORES )); then
        printf "# Skipping outer=%d, inner=%d → total=%d > CORES=%d\n" \
          "$o" "$i" "$total" "$CORES"
        continue
      fi

      python3 "${BENCH}" \
        --mode manyA \
        --n "${N}" \
        --S "${S}" \
        --outer "${o}" \
        --inner "${i}" \
        --pretty
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
