# Recuperar la Exhibición de Patos de Goma (Árbol Binario de Búsqueda)

**Dificultad:** Intermedio · **Puntos:** 100 · **Autoría:** Violeta

## Enunciado

Estás a cargo de una fábrica que organiza sus famosos patos de goma en una gran exhibición con forma de **árbol binario de búsqueda (BST)**. Cada pato de goma tiene impreso un número de serie único (`val`).

Para que la exhibición sea válida, los números de serie deben seguir la regla del BST: todos los patos en el subárbol izquierdo de un pato deben tener un número **menor**, y todos los del subárbol derecho un número **mayor**.

Por desgracia, un empleado novato tropezó en el almacén e **intercambió de lugar exactamente dos patos** de goma por error.

Tu misión es recuperar la exhibición original devolviendo a los dos patos a sus posiciones correctas **sin cambiar la estructura del árbol**.

## Ejemplos

### Ejemplo 1

```
Entrada: root = [1,3,null,null,2]
Salida:  [3,1,null,null,2]
```

**Explicación:** el pato con el número de serie 3 no puede ser el hijo izquierdo del pato 1 porque 3 > 1. Intercambiar los patos 1 y 3 hace que la exhibición vuelva a ser un BST válido.

### Ejemplo 2

```
Entrada: root = [3,1,4,null,null,2]
Salida:  [2,1,4,null,null,3]
```

**Explicación:** el pato número 2 no puede estar en el subárbol derecho del pato 3 porque 2 < 3. Intercambiando el pato 2 con el pato 3 se arregla la exhibición.

## Restricciones

- El número de patos (nodos) en la exhibición está en el rango `[2, 1000]`.
- `-2³¹ ≤ Node.val ≤ 2³¹ - 1`

## Pregunta de seguimiento (Follow up)

Una solución que utilice memoria O(n) es bastante directa de implementar (por ejemplo, anotando los números de todos los patos). ¿Podrías idear una solución utilizando espacio constante **O(1)** para no malgastar recursos en la fábrica?

## Entrega en la web

- La entrada contiene **100 árboles**, uno por línea, en recorrido por niveles.
- Entrega **100 listas JSON** de enteros y `null`, en el mismo orden.
- Cada árbol correcto vale 1 punto.
- Los espacios de presentación y los `null` finales opcionales no afectan al resultado.
- **No elimines los `null` interiores ni cambies la estructura.**
- El objetivo de memoria constante es opcional y no cambia la puntuación.

**Intentos:** 1 (compartido por el equipo).
