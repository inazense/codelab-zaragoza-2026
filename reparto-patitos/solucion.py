import sys
data = sys.stdin.read().split()
out = []
for k in range(0, len(data), 2):
    n, s = int(data[k]), data[k+1]
    h, objetivo = n // 2, s.count("A") // 2
    a = s[:h].count("A")
    c1 = 0
    while a != objetivo:
        a += (s[c1 + h] == "A") - (s[c1] == "A")
        c1 += 1
    out.append(f"{c1} {c1 + h}")
print("\n".join(out))