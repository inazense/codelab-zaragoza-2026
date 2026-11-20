import java.nio.file.*;
import java.util.*;

public class Estanque {

    static final int FILAS = 9;
    static final int CENTRO = 4;

    // Letra de zona de cada casilla del hexágono
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
        Scanner entrada = new Scanner(Path.of(fichero));
        int casos = entrada.nextInt();

        StringBuilder salida = new StringBuilder();
        for (int caso = 0; caso < casos; caso++) {
            String[] estanque = new String[FILAS];
            for (int i = 0; i < FILAS; i++) {
                estanque[i] = entrada.next(); // next() se salta las líneas en blanco
            }
            salida.append(enCalma(estanque)).append("\n");
        }

        Files.writeString(Path.of("salida.txt"), salida.toString());
        System.out.print(salida);
    }

    static boolean enCalma(String[] estanque) {
        // Cada pato se anota junto a su fila, sus dos diagonales y su zona.
        // Si una anotación ya existía, hay dos patos iguales en el mismo grupo.
        Set<String> vistos = new HashSet<>();

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < estanque[i].length(); j++) {
                char pato = estanque[i].charAt(j);
                if (pato == '.')
                    continue;

                int fila = i - CENTRO;
                int diagonalA = Math.max(-CENTRO, -CENTRO - fila) + j;
                int diagonalB = -diagonalA - fila;
                char zona = ZONAS[i].charAt(j);

                boolean repetido = !vistos.add("fila" + fila + pato)
                        || !vistos.add("diagA" + diagonalA + pato)
                        || !vistos.add("diagB" + diagonalB + pato)
                        || !vistos.add("zona" + zona + pato);
                if (repetido)
                    return false;
            }
        }
        return true;
    }
}
