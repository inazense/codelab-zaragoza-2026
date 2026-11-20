import java.io.*;
import java.util.*;

public class RecuperarPatos {

    static class Nodo {
        int pos;              // posición del valor en la lista original
        Nodo izq, der;
        Nodo(int pos) { this.pos = pos; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        String linea;
        while ((linea = in.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            out.append(resolver(linea)).append('\n');
        }
        System.out.print(out);
    }

    static String resolver(String linea) {
        // 1. Leer "[3,1,4,null,null,2]" como lista de textos
        String limpio = linea.trim().replace("[", "").replace("]", "").replace(" ", "");
        String[] t = limpio.split(",");

        // 2. Construir el árbol por niveles (cada nodo guarda su posición en t)
        Nodo raiz = new Nodo(0);
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        int i = 1;
        while (!cola.isEmpty() && i < t.length) {
            Nodo actual = cola.poll();
            if (i < t.length && !t[i].equals("null")) {
                actual.izq = new Nodo(i);
                cola.add(actual.izq);
            }
            i++;
            if (i < t.length && !t[i].equals("null")) {
                actual.der = new Nodo(i);
                cola.add(actual.der);
            }
            i++;
        }

        // 3. Recorrido inorden: en un BST correcto saldría ordenado
        List<Nodo> inorden = new ArrayList<>();
        recorrer(raiz, inorden);

        // 4. Buscar los dos patos fuera de sitio
        Nodo primero = null, segundo = null;
        for (int k = 0; k + 1 < inorden.size(); k++) {
            long a = Long.parseLong(t[inorden.get(k).pos]);
            long b = Long.parseLong(t[inorden.get(k + 1).pos]);
            if (a > b) {
                if (primero == null) primero = inorden.get(k);
                segundo = inorden.get(k + 1);
            }
        }

        // 5. Intercambiarlos en la lista original (la estructura no cambia)
        if (primero != null) {
            String tmp = t[primero.pos];
            t[primero.pos] = t[segundo.pos];
            t[segundo.pos] = tmp;
        }
        return "[" + String.join(",", t) + "]";
    }

    static void recorrer(Nodo n, List<Nodo> lista) {
        if (n == null) return;
        recorrer(n.izq, lista);
        lista.add(n);
        recorrer(n.der, lista);
    }
}