import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Solucion {

    // Devuelve el modelo que aparece más de n/2 veces
    static int modeloEstrella(int[] patitos) {
        int n = patitos.length;
        HashMap<Integer, Integer> contador = new HashMap<>();

        for (int modelo : patitos) {
            // sumamos 1 a las veces que hemos visto este modelo
            int veces = contador.getOrDefault(modelo, 0) + 1;
            contador.put(modelo, veces);

            // si ya supera la mitad, es la estrella
            if (veces > n / 2) {
                return modelo;
            }
        }
        return 0; // nunca se llega aquí: siempre hay estrella
    }

    public static void main(String[] args) throws Exception {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder salida = new StringBuilder();

        int t = Integer.parseInt(lector.readLine().trim());

        for (int caso = 0; caso < t; caso++) {
            int n = Integer.parseInt(lector.readLine().trim());

            String[] numeros = lector.readLine().trim().split(" ");
            int[] patitos = new int[n];
            for (int i = 0; i < n; i++) {
                patitos[i] = Integer.parseInt(numeros[i]);
            }

            salida.append(modeloEstrella(patitos)).append("\n");
        }

        System.out.print(salida);
    }
}
