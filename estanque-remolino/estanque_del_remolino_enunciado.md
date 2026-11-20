# El Estanque del Remolino

**Nivel:** Intermedio · **Puntos:** 100 · **Autoría:** Fernando · **Plazo:** 20 nov, 19:30

En el Parque Cuac hay un estanque con forma de hexágono, con 61 nenúfares: 5 por lado y 9 en la fila del medio. Por la tarde los críos dejan patitos de goma encima de los nenúfares, se van a casa, y por la noche los patitos se ponen a graznar. Los vecinos llaman a Don Pato, el guarda, casi todas las noches.

Hay 9 modelos de patito (dígitos 1–9).

Lo que pasa es que un patito no aguanta tener cerca a otro de su mismo modelo. Se lía a graznar en dos casos:

1. **Si se ven.** Dos patitos iguales en la misma línea recta de nenúfares se ven, aunque estén lejos o haya otros patitos en medio. Hay líneas en tres direcciones: la horizontal y dos diagonales.
2. **Si están en la misma zona.** En medio del estanque hay un remolino que lo divide en 7 zonas: la isla central I (el nenúfar del centro y sus 6 vecinos) y 6 corrientes A–F de 9 nenúfares, en forma de molinillo. Los patitos de la misma zona acaban encontrándose aunque no estén en línea.

## Mapa de zonas (fila a fila)

```
EDDDD
EEDDDC
EEEDDCC
EEEIICCC
FFFIIICCC
FFFIIBBB
FFAABBB
FAAABB
AAAAB
```

El estanque está **en calma** si no hay dos patitos del mismo modelo en ninguna línea horizontal, en ninguna diagonal (de ninguna de las dos direcciones) ni en ninguna zona.

Hay que decir si el estanque está en calma (`true`) o si esa noche Don Pato va a tener que salir con la linterna (`false`).

**Notas:**

- Solo importa cómo está el estanque ahora: uno a medio llenar puede estar en calma aunque luego sea imposible completarlo.
- Los nenúfares vacíos no cuentan.

## Cómo se describe un estanque

9 filas de arriba abajo, con los nenúfares de izquierda a derecha (filas de 5, 6, 7, 8, 9, 8, 7, 6, 5). Cada nenúfar es un dígito 1–9 o un `.` si está vacío. El nenúfar `i,j` es el de la fila `i`, posición `j`, contando desde 0.

## Ejemplos

### Ejemplo 1

```
..9.6
2..53.
.951..8
.1.9.7..
.5.7.314.
2..5....
.498.5.
..5...
1.467
```

**Salida:** `true`. No hay ningún par de patitos iguales que se vean ni que compartan zona.

### Ejemplo 2

```
..9.6
2..53.
.951..8
.1.9.7..
.5.7.314.
2..5.7..
.498.5.
..5...
1.467
```

**Salida:** `false`. Se añade un Chef (7) en 5,5. No está en línea con el Chef de 8,4, pero los dos están en la corriente B.

### Ejemplo 3

```
..9.6
2..53.
.951..8
.1.9.7..
.5.7.314.
2..5....
.498.5.
..5...
13467
```

**Salida:** `false`. El Bombero (3) nuevo de 8,1 y el de 4,5 están en zonas distintas, pero comparten diagonal.

## Restricciones

- Cada estanque tiene 9 filas, y la fila `i` mide `9 − |i − 4|`.
- Cada nenúfar es un dígito 1–9 o un `.`.

## Entrega

- **Entrada (`entrada.txt`):** la primera línea contiene T, el número de estanques. El fichero de competición contiene 100 estanques. Una línea en blanco precede a cada estanque, que ocupa nueve filas.
- **Salida (`salida.txt`):** `true` o `false` en minúsculas, una línea por estanque, en UTF-8. Cada caso correcto vale 1 punto.
- Un solo intento por equipo.
