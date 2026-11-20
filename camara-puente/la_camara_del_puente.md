# La Cámara del Puente

**Dificultad:** Fácil · **Puntos:** 50 · **Autoría:** Fernando
*Segunda parte de «El Desfile del Canal»*

## Enunciado

Este año Cuac TV, la tele local, retransmite el Gran Desfile. Han puesto una cámara en el puente, justo al lado de Don Pato, que no ha parado de mirar a cámara en toda la mañana.

Los `n` patitos bajan en fila india, cada uno con su código de modelo en la chapa. Igual que en el desfile, el dibujo se repite cada 9 códigos: lo que cuenta es la chapa.

La cámara es la que es: encuadra siempre `k` patitos seguidos y no tiene zoom. Empieza con los patitos `0` a `k − 1` y avanza de uno en uno hasta que entra el último: `n − k + 1` encuadres.

En cada encuadre, una pantalla muestra el **modelo estrella** del encuadre: el que aparece más de `⌊k / 2⌋` veces entre los `k` patitos. Si no hay, muestra «—» y al comentarista le toca rellenar como pueda.

Hay que sacar lo que va a mostrar la pantalla en cada encuadre. El valor `i` es el modelo estrella del encuadre que empieza en el patito `i`, o «—» si no hay. En el fichero de salida, «—» se escribe `-`.

## Ejemplos

### Ejemplo 1

- **Entrada:** `patitos = [3,2,3,1,2,2,1,1]`, `k = 3`
- **Salida:** `3 - - 2 2 1`

**Explicación:** hace falta aparecer más de `⌊3 / 2⌋ = 1` vez. En la imagen, el encuadre 3 `(1, 2, 2)` es para los Piratas. En los encuadres 1 y 2 no se repite ningún modelo, así que sale «—».

### Ejemplo 2

- **Entrada:** `patitos = [2,2,1,1,1,2,2]`, `k = 4`
- **Salida:** `- 1 1 -`

**Explicación:** en el encuadre 0 `(2, 2, 1, 1)` hay empate, y hace falta más de `⌊4 / 2⌋ = 2`. Un empate no vale.

### Ejemplo 3

- **Entrada:** `patitos = [2,2,1,1,1,2,2]`, `k = 7`
- **Salida:** `2`

**Explicación:** con `k = n` hay un solo encuadre y es el problema de la primera parte. Vamos, que sale lo mismo que en el desfile.

## Restricciones

- `n == patitos.length`
- `1 <= k <= n <= 10^5`
- `-10^9 <= patitos[i] <= 10^9`
- No se garantiza que haya estrella en ningún encuadre.

## Entrega

**Entrada (`entrada.txt`).** La primera línea contiene `T`, el número de desfiles. El fichero de competición contiene 50 desfiles. Cada desfile ocupa dos líneas:

1. La primera contiene `n k`.
2. La segunda contiene los `n` códigos separados por espacios.

**Salida (`salida.txt`).** Escribe una línea por desfile, con todos sus encuadres en orden. Separa los valores con un solo espacio. Escribe un guion ASCII (`-`) si un encuadre no tiene estrella. La línea completa es una unidad puntuable. Entrega el archivo de texto en UTF-8. Cada caso correcto vale 1 punto.

Los archivos `ejemplo-entrada.txt` y `ejemplo-salida.txt` contienen los ejemplos.

## Reglas del reto

- Un reto abierto por equipo y un intento por reto (los integrantes del equipo comparten los intentos).
- Se puede enviar a partir de un minuto después de abrir el reto, con al menos un minuto entre entregas.
- Los equipos ganadores deberán explicar sus soluciones. En intermedios y difíciles también tendrán que probarlas con entradas nuevas.
- Plazo: hasta el 20 nov, 19:30.
