import java.io.*;
import java.util.*;

public class Solucion {

    public static void main(String[] args) throws IOException {
        // Uso: java Solucion input.txt salida.txt
        BufferedReader lector = new BufferedReader(new FileReader(args[0]));
        PrintWriter salida = new PrintWriter(new FileWriter(args[1]));

        int T = Integer.parseInt(lector.readLine().trim());
        for (int caso = 0; caso < T; caso++) {
            String[] primera = lector.readLine().trim().split(" ");
            int n = Integer.parseInt(primera[0]);
            int k = Integer.parseInt(primera[1]);

            int[] patitos = new int[n];
            String[] segunda = lector.readLine().trim().split(" ");
            for (int i = 0; i < n; i++) {
                patitos[i] = Integer.parseInt(segunda[i]);
            }

            salida.println(resolver(patitos, k));
        }
        salida.close();
    }

    static String resolver(int[] patitos, int k) {
        int n = patitos.length;
        int mitad = k / 2;

        Map<Integer, Integer> contador = new HashMap<>();
        boolean hayEstrella = false;
        int estrella = 0;

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < n; i++) {
            // 1) Si el encuadre ya está lleno, sale el patito más antiguo
            if (i >= k) {
                int sale = patitos[i - k];
                contador.put(sale, contador.get(sale) - 1);
                if (hayEstrella && sale == estrella && contador.get(sale) <= mitad) {
                    hayEstrella = false;         // ya no llega a la mitad
                }
            }

            // 2) Entra el patito nuevo
            int entra = patitos[i];
            int veces = contador.getOrDefault(entra, 0) + 1;
            contador.put(entra, veces);
            if (veces > mitad) {
                hayEstrella = true;
                estrella = entra;
            }

            // 3) Si ya hay k patitos en el encuadre, anotamos la pantalla
            if (i >= k - 1) {
                if (resultado.length() > 0) resultado.append(' ');
                resultado.append(hayEstrella ? String.valueOf(estrella) : "-");
            }
        }
        return resultado.toString();
    }
}
