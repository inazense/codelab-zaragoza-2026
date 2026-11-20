# El Desfile del Canal

**Dificultad:** Básico · **Puntos:** 25 (25 puntos de clasificación · 25 puntos máximos para tiradas) · **Autoría:** Fernando

## Enunciado

Cada primavera la fábrica de patitos de goma hace su Gran Desfile: `n` patitos bajan en fila india por el canal del Parque Cuac y pasan por debajo del puente, donde está Don Pato, el guarda, mirando que nadie se adelante.

Cada patito lleva una chapa con su código de modelo, un número entero. El catálogo clásico va del 1 al 9:

*(imagen del catálogo en la página original)*

Pero la fábrica tiene muchísimos más modelos, y algunos llevan código negativo (nadie en la fábrica sabe explicar por qué). Como solo hay 9 dibujos de patito, el aspecto se repite: estos cuatro son Bomberos, pero son cuatro modelos distintos. Lo que cuenta es la chapa.

*(imagen de los cuatro Bomberos en la página original)*

La fábrica va diciendo por ahí que este año tiene un **modelo estrella**, uno que sale más de `⌊n / 2⌋` veces en el desfile. Lo que no ha dicho es cuál.

Dado el desfile (`patitos[i]` es el código del patito en la posición `i`), hay que averiguar cuál es el modelo estrella. Esta vez no es marketing: siempre existe.

## Ejemplos

### Ejemplo 1

- **Entrada:** `patitos = [3,2,3]`
- **Salida:** `3`

**Explicación:** 2 Bomberos (3) de 3 patitos, y 2 es más que `⌊3 / 2⌋ = 1`.

### Ejemplo 2

- **Entrada:** `patitos = [2,2,1,1,1,2,2]`
- **Salida:** `2`

**Explicación:** 4 Piratas (2) de 7, y 4 es más que `⌊7 / 2⌋ = 3`. Ojo con los tres Clásicos seguidos del medio: son el grupo más largo, pero lo que cuenta es el total.

## Restricciones

- `n == patitos.length`
- `1 <= n <= 5 · 10^4`
- `-10^9 <= patitos[i] <= 10^9`
- Siempre hay un modelo estrella.

## Entrega

**Entrada (`entrada.txt`).** La primera línea contiene `T`, el número de desfiles. Cada desfile ocupa dos líneas:

1. La primera contiene `n`.
2. La segunda contiene los `n` códigos separados por espacios.

**Salida (`salida.txt`).** Escribe un código de modelo por línea, en el orden de los desfiles. Entrega el archivo de texto en UTF-8. Cada caso correcto vale 5 puntos.

Los archivos `ejemplo-entrada.txt` y `ejemplo-salida.txt` contienen los ejemplos.

## Reglas del reto

- Hasta 3 retos abiertos por equipo y un intento por reto (los integrantes del equipo comparten los intentos).
- Se puede enviar a partir de un minuto después de abrir un reto, con al menos un minuto entre entregas.
- Los equipos ganadores deberán explicar sus soluciones. En intermedios y difíciles también tendrán que probarlas con entradas nuevas.
- Plazo: hasta el 20 nov, 19:30.
