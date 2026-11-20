import sys
from collections import defaultdict

def encuadres(p, k):
    n = len(p)
    cnt = defaultdict(int)
    for x in p[:k]:
        cnt[x] += 1
    lim = k // 2
    # estrella inicial: buscar en la primera ventana
    estrella = next((x for x, c in cnt.items() if c > lim), None)
    res = [estrella]
    for i in range(k, n):
        cnt[p[i - k]] -= 1          # sale el patito de la izquierda
        cnt[p[i]] += 1              # entra el nuevo
        # la estrella nueva solo puede ser la anterior o la que acaba de entrar
        if estrella is not None and cnt[estrella] > lim:
            pass
        elif cnt[p[i]] > lim:
            estrella = p[i]
        else:
            estrella = None
        res.append(estrella)
    return " ".join("-" if e is None else str(e) for e in res)

def main():
    data = sys.stdin.read().split()
    t = int(data[0]); pos = 1
    out = []
    for _ in range(t):
        n, k = int(data[pos]), int(data[pos + 1]); pos += 2
        p = [int(x) for x in data[pos:pos + n]]; pos += n
        out.append(encuadres(p, k))
    sys.stdout.write("\n".join(out) + "\n")

if __name__ == "__main__":
    main()
