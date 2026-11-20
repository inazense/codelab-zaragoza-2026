# La Gran Carrera de Patos del Río Real

**Dificultad:** Fácil · **Puntos:** 50 · **Autoría:** Nicolás

## Contexto

Mañana a primera hora se celebra la tradicional carrera benéfica de patos de goma por el río, con **N** patos participantes numerados del 1 al N (1 ≤ N ≤ 1000). La confederación hidrográfica ha interceptado una amenaza: un saboteador ha untado la base de **uno y solo uno** de los patos con una toxina fluorescente de acción retardada. Si ese pato llega a tocar el agua del cauce, contaminará todo el río y obligará a suspender la carrera.

La toxina tarda una noche entera en activarse: si una muestra entra en contacto con líquido reactivo, el recipiente amanecerá teñido de verde fosforito tras varias horas de reposo; si el agua sigue limpia, no contenía toxina.

Para evitar preparar cientos de análisis individuales y no fundirse el presupuesto en reactivo, el equipo técnico debe optimizar los recursos al máximo:

- Se dispone de una única noche antes de soltar los patos al río.
- Se debe emplear el **número mínimo estricto** de cubos de ensayo, numerados correlativamente a partir del 1 (1, 2, …, K).
- A cada pato se le pasa un hisopo por la base y se diluye parte de la muestra en una combinación concreta de cubos, siguiendo un protocolo sistemático según el dorsal del pato.
- Los cubos reposan toda la noche en el laboratorio.

Al amanecer, la combinación de cubos que hayan reaccionado revelará de forma unívoca qué pato es el saboteado para retirarlo antes de la salida.

## Formato de entrada

El archivo de entrada contiene una serie de casos de prueba consecutivos. Cada caso consta exactamente de 3 líneas:

1. **Línea 1:** número total de patos participantes `N`.
2. **Línea 2:** dorsal de un pato concreto `P` sobre el que la organización pregunta: «¿En qué cubos de ensayo se diluyó la muestra de este pato?»
3. **Línea 3:** identificadores de los cubos que han amanecido teñidos de verde fosforito (separados por espacios; línea vacía si no ha reaccionado ninguno).

## Formato de salida

Por cada caso de prueba, imprimir una única línea con el formato exacto:

```
K_CUBOS, [CUBOS_PATO_P], PATO_SABOTEADO
```

Donde:

- **K_CUBOS:** número entero con la cantidad mínima de cubos necesarios para N patos.
- **[CUBOS_PATO_P]:** lista de cubos donde se diluyó la muestra del pato P, entre corchetes y con identificadores separados por espacios (en orden ascendente). Si la muestra no fue a ningún cubo, se representa como `[]`.
- **PATO_SABOTEADO:** número entero con el dorsal del pato localizado tras procesar las reacciones matutinas.

## Ejemplo de referencia

Para comprobar que tu algoritmo sigue el mismo orden y criterio de asignación que el laboratorio, toma como base este caso:

**input_ejemplo.txt**

```
4
2
2
```

**output_ejemplo.txt**

```
2, [1], 1
```

### Pistas del caso de referencia

- Con 4 patos, el laboratorio solo necesita preparar 2 cubos para cubrir todas las combinaciones posibles de reacciones al día siguiente.
- Para el pato 2, la muestra solo se vierte en el cubo 1.
- Si tras la noche el único que amanece verde es el cubo 2, el protocolo determina que el pato untado con toxina es el pato 1.
- **Regla de descarte:** si ningún cubo amanece teñido, la muestra contaminada corresponde al pato restante que no formó parte de ninguna mezcla.

## Entrega

Entrega 50 líneas, una por caso. Cada caso correcto vale 1 punto. Se ignoran los espacios alrededor de comas y corchetes; conserva el orden ascendente de los cubos y sepáralos con espacios.

**Intentos:** 1 (compartido por el equipo).
