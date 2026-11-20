import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Microcorrientes marinas - Unicode 2026 (CodeLab)
 *
 * Regla (mensaje ROT13 descifrado): pato + gps <= 100 gramos.
 * Para cada caso: máximo número de parejas pato + gps que no superan el límite.
 *
 * Uso:
 *   javac Microcorrientes.java
 *   java Microcorrientes entrada.json > salida.txt
 *
 * No necesita librerías: el JSON se lee con expresiones regulares.
 */
public class Microcorrientes {

    static final int LIMITE = 100;

    /** Voraz con dos punteros: GPS más pesado que quede con el pato más ligero que quede. */
    static int maxParejas(int[] patos, int[] gps) {
        Arrays.sort(patos);                 // patos de ligero a pesado
        Arrays.sort(gps);                   // gps de ligero a pesado (los recorremos al revés)
        int i = 0;                          // pato más ligero libre
        int parejas = 0;
        for (int j = gps.length - 1; j >= 0 && i < patos.length; j--) {
            if (patos[i] + gps[j] <= LIMITE) {   // cabe -> pareja
                parejas++;
                i++;
            }
            // si no cabe, este GPS no cabe con ningún pato: se descarta
        }
        return parejas;
    }

    static int[] parseNums(String s) {
        Matcher m = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(s);
        List<Integer> out = new ArrayList<>();
        while (m.find()) out.add((int) Math.round(Double.parseDouble(m.group())));
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) throws Exception {
        String ruta = args.length > 0 ? args[0] : "entrada.json";
        String json = Files.readString(Path.of(ruta), StandardCharsets.UTF_8);

        // Formato esperado: [{"patos":[...], "gps":[...]}, ...]
        Matcher mp = Pattern.compile("\"patos\"\\s*:\\s*\\[([^\\]]*)\\]").matcher(json);
        Matcher mg = Pattern.compile("\"gps\"\\s*:\\s*\\[([^\\]]*)\\]").matcher(json);
        List<int[]> patos = new ArrayList<>(), gps = new ArrayList<>();
        while (mp.find()) patos.add(parseNums(mp.group(1)));
        while (mg.find()) gps.add(parseNums(mg.group(1)));

        // Alternativa sin claves: [[[patos],[gps]], ...] -> listas internas de dos en dos
        if (patos.isEmpty()) {
            Matcher ma = Pattern.compile("\\[([^\\[\\]]*)\\]").matcher(json);
            List<int[]> listas = new ArrayList<>();
            while (ma.find()) listas.add(parseNums(ma.group(1)));
            for (int k = 0; k + 1 < listas.size(); k += 2) {
                patos.add(listas.get(k));
                gps.add(listas.get(k + 1));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < patos.size(); k++) {
            sb.append(maxParejas(patos.get(k), gps.get(k))).append('\n');
        }
        System.out.print(sb);
        System.err.println("Casos procesados: " + patos.size());
    }
}
