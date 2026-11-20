t = open("patogoma.txt").read().strip()
bits = "".join("1" if c.isupper() else "0" for c in t)
print("".join(chr(int(bits[i:i+8], 2)) for i in range(0, len(bits), 8)))