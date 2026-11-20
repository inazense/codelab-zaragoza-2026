"""
Microcorrientes marinas - Unicode 2026 (CodeLab)

Regla (mensaje ROT13 descifrado): pato + gps <= 100 gramos.
Para cada caso, máximo número de parejas pato + gps que no superan el límite.

Uso:
    python3 microcorrientes.py entrada.json > salida.txt
"""
import json
import sys

LIMITE = 100


def max_parejas(patos, gps, limite=LIMITE):
    patos = sorted(patos)                 # de ligero a pesado
    gps = sorted(gps, reverse=True)       # de pesado a ligero
    i = 0                                 # pato más ligero que queda
    parejas = 0
    for g in gps:                         # recorremos los gps del más pesado al más ligero
        if i == len(patos):
            break                         # no quedan patos
        if patos[i] + g <= limite:        # cabe con el pato más ligero -> pareja
            parejas += 1
            i += 1
        # si no cabe, este gps no cabe con ningún pato: se descarta
    return parejas


def main():
    ruta = sys.argv[1] if len(sys.argv) > 1 else "entrada.json"
    with open(ruta, encoding="utf-8") as f:
        casos = json.load(f)

    # Admite {"casos": [...]} o directamente una lista
    if isinstance(casos, dict):
        casos = next(v for v in casos.values() if isinstance(v, list))

    for caso in casos:
        if isinstance(caso, dict):
            patos, gps = caso["patos"], caso["gps"]
        else:                              # por si viene como [patos, gps]
            patos, gps = caso
        print(max_parejas(patos, gps))


if __name__ == "__main__":
    main()
