import matplotlib.pyplot as plt

# Parallel degree p
p_vals = [1, 2, 4, 8]

# Throughput data (from your benchmark)
# Coarse (manyA)
coarse_M1_thr = [337.93, 426.68, 453.32, 432.95]
coarse_M4_thr = [671.98, 919.85, 1106.38, 1130.48]

# Fine (sameA)
fine_M1_thr   = [574688.16, 659297.62, 656199.88, 567680.32]
fine_M4_thr   = [928660.05, 1001956.95, 956859.11, 965429.33]

def rel(thr):
    base = thr[0]
    return [t / base for t in thr]

coarse_M1_rel = rel(coarse_M1_thr)
coarse_M4_rel = rel(coarse_M4_thr)
fine_M1_rel   = rel(fine_M1_thr)
fine_M4_rel   = rel(fine_M4_thr)

plt.figure(figsize=(8, 4.5))

# 2020 M1 (orange)
plt.plot(
    p_vals, coarse_M1_rel,
    color="tab:orange", linestyle="-", marker="o",
    label="Coarse, 2020 M1",
)
plt.plot(
    p_vals, fine_M1_rel,
    color="tab:orange", linestyle=":", marker="s",
    label="Fine, 2020 M1",
)

# 2024 M4 Pro (blue)
plt.plot(
    p_vals, coarse_M4_rel,
    color="tab:blue", linestyle="-", marker="o",
    label="Coarse, 2024 M4 Pro",
)
plt.plot(
    p_vals, fine_M4_rel,
    color="tab:blue", linestyle=":", marker="s",
    label="Fine, 2024 M4 Pro",
)

plt.xlabel(r"Parallel degree $p$ (outer or inner)")
plt.ylabel(r"Relative throughput ($\times$ baseline at $p=1$)")
plt.title("Relative speedup of coarse vs fine grained parallelism")
plt.xticks(p_vals)
plt.grid(True, linestyle=":")
plt.legend()
plt.tight_layout()

plt.show()
plt.savefig("benchmark_speedup_relative.png")
