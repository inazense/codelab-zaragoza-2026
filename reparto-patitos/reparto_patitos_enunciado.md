# El Reparto de los Patitos de Goma

**Nivel:** Fácil · **Puntos:** 50 · **Autoría:** Nicolás · **Plazo:** 20 nov, 19:30

## Contexto

Dos organizadores de la gran carrera anual de patitos de goma tienen una fila recta con N patitos junto a la orilla del río, numerados desde la posición 0 hasta la N−1.

En la fila hay exactamente dos modelos de patitos mezclados:

- **A:** patito clásico amarillo.
- **B:** patito pirata con parche.

Ambos organizadores quieren dividir el lote en igualdad de condiciones: cada uno debe quedarse con exactamente la mitad del total de patitos A y exactamente la mitad del total de patitos B.

El primer organizador usa una caja rígida con capacidad exacta para la mitad de la fila (N/2 patitos consecutivos). Camina desde el principio de la fila (índice 0) hacia el final, deslizando la caja posición a posición hasta encontrar el primer tramo donde el contenido de la caja cumpla el reparto equitativo.

Al encajar la caja entre un índice de inicio `c1` y un índice de fin `c2 = c1 + N/2`:

- El primer organizador se lleva los patitos dentro de la caja: intervalo `[c1, c2 − 1]`.
- El segundo se queda con los de fuera, en ambos extremos: `[0, c1 − 1]` y `[c2, N − 1]`.

## Objetivo

Dada la disposición inicial de los N patitos, determina los índices `c1` y `c2` donde el primer organizador debe detener la caja.

Si hay varias posiciones válidas, se elige la primera desde el inicio (la de menor `c1`).

## Formato de entrada

- **Línea 1:** un entero par N (4 ≤ N ≤ 10^6).
- **Línea 2:** una cadena de longitud N con solo 'A' y 'B'. Se garantiza que el total de 'A' y el total de 'B' son pares y ≥ 2.

## Formato de salida

Una línea con dos enteros separados por un espacio: `c1 c2` (inicio inclusivo, fin exclusivo), con 0 ≤ c1 < c2 ≤ N y c2 − c1 = N/2.

## Ejemplo

**Entrada (sample.in)**

```
18
AAAAABBAAABAABBAAB
```

**Salida (sample.out)**

```
2 11
```

**Explicación:** hay 18 patitos, 12 'A' y 6 'B'. La caja abarca 9 patitos y debe contener exactamente 6 'A' y 3 'B'.

- `0 9` (AAAAABBAA): 7 'A' y 2 'B', inválido.
- `1 10` (AAAABBAAA): 7 'A' y 2 'B', inválido.
- `2 11` (AAABBAAAB): 6 'A' y 3 'B'. Primera posición válida, así que el resultado es `2 11`.

## Entrega en la web

- El archivo de competición contiene **50 casos seguidos**. Cada caso ocupa dos líneas, sin separadores.
- Entrega **50 líneas** en el mismo orden, con los dos índices de cada caso separados por un solo espacio.
- Cada caso correcto vale 1 punto. Un solo intento por equipo.
