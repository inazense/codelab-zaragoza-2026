import sys

def estrella(p):
    # Boyer-Moore: el modelo estrella sobrevive a todas las "cancelaciones"
    cand, votos = None, 0
    for x in p:
        if votos == 0:
            cand = x
        votos += 1 if x == cand else -1
    return cand

def main():
    data = sys.stdin.read().split()
    t = int(data[0]); pos = 1
    out = []
    for _ in range(t):
        n = int(data[pos]); pos += 1
        out.append(str(estrella([int(x) for x in data[pos:pos + n]]))); pos += n
    sys.stdout.write("\n".join(out) + "\n")

if __name__ == "__main__":
    main()
