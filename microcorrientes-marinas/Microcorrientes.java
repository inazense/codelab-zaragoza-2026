import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

// java Microcorrientes entrada.json > salida.txt
public class Microcorrientes {

    static final int LIMITE = 100;

    // Empareja cada pato con un GPS sin superar LIMITE de peso conjunto.
    // Los patos más ligeros se emparejan con los GPS más pesados que les caben.
    static int maxParejas(int[] patos, int[] gps) {
        Arrays.sort(patos);
        Arrays.sort(gps);

        int parejas = 0;
        int pato = 0; // índice del pato más ligero sin pareja
        for (int g = gps.length - 1; g >= 0 && pato < patos.length; g--) {
            if (patos[pato] + gps[g] <= LIMITE) {
                parejas++;
                pato++;
            }
        }
        return parejas;
    }

    // Extrae todos los números de un texto, redondeando los decimales.
    static int[] numeros(String texto) {
        Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(texto);
        List<Integer> lista = new ArrayList<>();
        while (m.find()) {
            lista.add((int) Math.round(Double.parseDouble(m.group())));
        }
        return lista.stream().mapToInt(Integer::intValue).toArray();
    }

    // Devuelve el contenido numérico de cada coincidencia del patrón (grupo 1).
    static List<int[]> buscarListas(String json, String patron) {
        Matcher m = Pattern.compile(patron).matcher(json);
        List<int[]> listas = new ArrayList<>();
        while (m.find()) {
            listas.add(numeros(m.group(1)));
        }
        return listas;
    }

    public static void main(String[] args) throws Exception {
        String ruta = args.length > 0 ? args[0] : "entrada.json";
        String json = Files.readString(Path.of(ruta));

        // Formato 1: [{"patos":[...], "gps":[...]}, ...]
        List<int[]> patos = buscarListas(json, "\"patos\"\\s*:\\s*\\[([^\\]]*)\\]");
        List<int[]> gps = buscarListas(json, "\"gps\"\\s*:\\s*\\[([^\\]]*)\\]");

        // Formato 2 (sin claves): [[[patos],[gps]], ...] -> listas internas de dos en dos
        if (patos.isEmpty()) {
            List<int[]> todas = buscarListas(json, "\\[([^\\[\\]]*)\\]");
            for (int i = 0; i + 1 < todas.size(); i += 2) {
                patos.add(todas.get(i));
                gps.add(todas.get(i + 1));
            }
        }

        for (int i = 0; i < patos.size(); i++) {
            System.out.println(maxParejas(patos.get(i), gps.get(i)));
        }
        System.err.println("Casos procesados: " + patos.size());
    }
}
