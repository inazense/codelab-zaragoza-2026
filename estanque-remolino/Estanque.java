import java.nio.file.*;
import java.util.*;

public class Estanque {

    static final String[] ZONAS = {
        "EDDDD",
        "EEDDDC",
        "EEEDDCC",
        "EEEIICCC",
        "FFFIIICCC",
        "FFFIIBBB",
        "FFAABBB",
        "FAAABB",
        "AAAAB"
    };

    public static void main(String[] args) throws Exception {
        String fichero = args.length > 0 ? args[0] : "entrada.txt";
        Scanner sc = new Scanner(Path.of(fichero));
        int t = sc.nextInt();

        StringBuilder salida = new StringBuilder();
        for (int caso = 0; caso < t; caso++) {
            String[] filas = new String[9];
            for (int i = 0; i < 9; i++) filas[i] = sc.next();   // next() se salta las líneas en blanco
            salida.append(enCalma(filas)).append("\n");
        }

        Files.writeString(Path.of("salida.txt"), salida.toString());
        System.out.print(salida);
    }

    static boolean enCalma(String[] filas) {
        // Guardamos "grupo + modelo" de cada patito que vamos viendo
        Set<String> vistos = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < filas[i].length(); j++) {
                char pato = filas[i].charAt(j);
                if (pato == '.') continue;

                // Coordenadas del hexágono: la fila y las dos diagonales
                int fila = i - 4;
                int inicio = Math.max(-4, -4 - fila);
                int diag1 = inicio + j;
                int diag2 = -diag1 - fila;
                char zona = ZONAS[i].charAt(j);

                // Si ya había un pato igual en la misma fila, diagonal o zona -> graznidos
                if (!vistos.add("F" + fila + pato)) return false;
                if (!vistos.add("D" + diag1 + pato)) return false;
                if (!vistos.add("E" + diag2 + pato)) return false;
                if (!vistos.add("Z" + zona + pato)) return false;
            }
        }
        return true;
    }
}