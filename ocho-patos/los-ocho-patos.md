# Los ocho patos

**Dificultad:** Fácil · **Puntos:** 50 · **Autoría:** Lorien
**Evento:** Unicode 2026 (CodeLab)

Estos 8 patos tienen un problema: o dicen la verdad, o mienten siempre. Cada pato hace una afirmación y una regla para transformar números.

## El reto

1. Averigua qué patos dicen la verdad y cuáles mienten.
2. Sobre un fichero con 100 números enteros separados por espacios, aplica a esos 100 números, **EN ORDEN** (del Pato 1 al Pato 8), la regla de cada pato que dice la verdad. Las reglas de los patos mentirosos se ignoran.
3. Cada regla se aplica a **TODOS** los números de la lista, y el resultado de una regla es la entrada de la siguiente.
4. Al final, suma los 100 números. Ese resultado es la respuesta del reto.

## Reglas de los patos

- **Pato 1:** Multiplica por 3 todos los números impares.
- **Pato 2:** Suma 5 a todos los números menores de 10.
- **Pato 3:** Divide entre 2 todos los números pares.
- **Pato 4:** Suma las cifras de todos los números de dos cifras, del 10 al 99 (por ejemplo, 50 pasa a ser 5 y 37 pasa a ser 3 + 7 = 10).
- **Pato 5:** Duplica (multiplica por 2) todos los números que terminen en un dígito par.
- **Pato 6:** Sustituye por 0 todos los números que sean múltiplo de 5.
- **Pato 7:** Suma 1 a todos los números.
- **Pato 8:** Sustituye por 0 todos los números negativos.

## Afirmaciones

1. **Pato 1:** Exactamente ninguno de los patos del 4 al 8 dice la verdad.
2. **Pato 2:** El pato 5 y el pato 7 tienen estados distintos.
3. **Pato 3:** Hay exactamente cuatro patos veraces.
4. **Pato 4:** El pato 3 y el pato 4 tienen estados distintos.
5. **Pato 5:** Ni el pato 7 ni el pato 8 dicen la verdad.
6. **Pato 6:** Hay más patos veraces con número par que con número impar.
7. **Pato 7:** Al menos uno de los patos 2 y 3 miente.
8. **Pato 8:** La suma de los números de los patos veraces es múltiplo de 3.

## Aclaraciones

- "Estados distintos" significa que uno de los dos patos miente y el otro dice la verdad.
- En la afirmación del Pato 8, lo que se suma son los **NÚMEROS DE LOS PATOS** que dicen la verdad (por ejemplo, si dijeran la verdad los patos 1, 4 y 6, la suma sería 1 + 4 + 6 = 11). No es la suma de cuántos patos dicen la verdad.

## Entrega

Un entero en una línea: la suma final. Una respuesta final, una unidad puntuable.

> Intentos: 1 de 1 (compartidos por el equipo).
