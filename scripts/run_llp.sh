#!/bin/bash
# =====================================================================
# Run all algorithm tests individually with clear headers (macOS/bash)
# =====================================================================

set -e
cd "$(dirname "$0")/.."  # move from scripts/ to repo root

echo "=============================================================="
echo " Cleaning previous builds..."
echo "=============================================================="
./gradlew clean > /dev/null

run_test () {
  local test_class=$1
  echo
  echo "=============================================================="
  echo " Running $test_class"
  echo "=============================================================="
  ./gradlew test --tests "edu.utexas.ece.it.${test_class}" 
}

# ---------------------------------------------------------------------
# Individual algorithm test classes (add or remove as needed)
# ---------------------------------------------------------------------
run_test "PrefixSumTest"
run_test "StableMarriageTest"
run_test "JohnsonTest"
run_test "BoruvkaTest"
run_test "BellmanFordTest"
run_test "FastComponentTest"

echo
echo "=============================================================="
echo " All algorithm tests completed successfully"
echo "=============================================================="

