import java.io.*;
import java.util.*;

public class CarreraPatos {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        String linea;
        while ((linea = siguienteNoVacia(in)) != null) {
            int n = Integer.parseInt(linea.trim());
            int p = Integer.parseInt(siguienteNoVacia(in).trim());
            String verdes = in.readLine();          // puede venir vacía
            if (verdes == null) verdes = "";

            int k = cubosNecesarios(n);
            out.append(k).append(", ")
               .append(cubosDelPato(p, k)).append(", ")
               .append(patoSaboteado(verdes, n, k)).append('\n');
        }
        System.out.print(out);
    }

    // Mínimo K tal que 2^K >= N
    static int cubosNecesarios(int n) {
        int k = 0;
        while ((1 << k) < n) k++;
        return k;
    }

    // Dorsal en binario con K bits; el cubo 1 es el bit más significativo.
    // Si N = 2^K, el último pato queda en 0 y no va a ningún cubo.
    static String cubosDelPato(int p, int k) {
        int codigo = p % (1 << k);
        List<String> cubos = new ArrayList<>();
        for (int cubo = 1; cubo <= k; cubo++) {
            int bit = k - cubo;
            if ((codigo >> bit & 1) == 1) cubos.add(String.valueOf(cubo));
        }
        return "[" + String.join(" ", cubos) + "]";
    }

    // Reconstruye el número con los cubos verdes; si no hay ninguno, es el pato N.
    static int patoSaboteado(String verdes, int n, int k) {
        int codigo = 0;
        for (String s : verdes.trim().split("\\s+")) {
            if (s.isEmpty()) continue;
            int cubo = Integer.parseInt(s);
            codigo |= 1 << (k - cubo);
        }
        return codigo == 0 ? n : codigo;
    }

    static String siguienteNoVacia(BufferedReader in) throws IOException {
        String l;
        while ((l = in.readLine()) != null && l.trim().isEmpty()) { }
        return l;
    }
}