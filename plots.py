import os
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

df = pd.read_csv("results/results.csv")
med = df.groupby(["algorithm", "input", "n"], as_index=False).median(numeric_only=True)
os.makedirs("results/plots", exist_ok=True)

def get(alg, inp):
    return med[(med.algorithm == alg) & (med.input == inp)].sort_values("n")

# Plot 1: Recursive, operations vs n (log y)
plt.figure()
for inp in ["random", "worst_case"]:
    d = get("Recursive", inp)
    plt.plot(d.n, d.counter, marker="o", label=inp)
plt.yscale("log")
plt.title("Recursive Edit Distance: operations vs n")
plt.xlabel("String length n (characters)")
plt.ylabel("Operations (log scale)")
plt.legend(); plt.grid(True)
plt.savefig("results/plots/1_recursive_ops.png", dpi=150); plt.close()

# Plot 2: Table vs TwoRows, time vs n (random)
plt.figure()
for alg in ["Table", "TwoRows"]:
    d = get(alg, "random")
    d = d[d.n >= 100]
    plt.plot(d.n, d.time_ms, marker="o", label=alg)
plt.title("Edit Distance: Table vs TwoRows (random input)")
plt.xlabel("String length n (characters)")
plt.ylabel("Median time (ms)")
plt.legend(); plt.grid(True)
plt.savefig("results/plots/2_table_vs_tworows_time.png", dpi=150); plt.close()

# Plot 3: Naive vs KMP, comparisons vs n (log-log)
plt.figure()
for alg in ["Naive", "KMP"]:
    for inp, style in [("random", "--"), ("worst_case", "-")]:
        d = get(alg, inp)
        plt.plot(d.n, d.counter, style, marker="o", label=f"{alg} {inp}")
plt.xscale("log"); plt.yscale("log")
plt.title("String search: character comparisons vs n")
plt.xlabel("Text length n (characters, log scale)")
plt.ylabel("Comparisons (log scale)")
plt.legend(); plt.grid(True)
plt.savefig("results/plots/3_search_comparisons.png", dpi=150); plt.close()

# Plot 4: ratio Naive/KMP comparisons
plt.figure()
for inp in ["random", "worst_case"]:
    nv = get("Naive", inp).set_index("n").counter
    km = get("KMP", inp).set_index("n").counter
    r = (nv / km)
    plt.plot(r.index, r.values, marker="o", label=inp)
plt.title("Naive / KMP comparisons ratio")
plt.xlabel("Text length n (characters)")
plt.ylabel("Ratio (Naive comparisons / KMP comparisons)")
plt.legend(); plt.grid(True)
plt.savefig("results/plots/4_ratio_naive_kmp.png", dpi=150); plt.close()

print("Plots saved to results/plots/")