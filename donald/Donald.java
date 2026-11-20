import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.*;

/**
 * Reto "Donald" - Unicode 2026 (CodeLab)
 *
 * Busca nombres de ciudades en una sopa de letras.
 *   - Solo cuentan las ciudades de 5 letras o más (las de <= 4 "las araña": aparecen por azar).
 *   - Los nombres compuestos se juntan en una sola palabra (Nueva York -> nuevayork).
 *   - Se ignoran tildes, guiones, apóstrofos y espacios.
 *   - Se busca en las 8 direcciones ("da vueltas") y, opcionalmente, saliendo por un
 *     borde y entrando por el opuesto.
 *
 * Uso:
 *   javac Donald.java
 *   java Donald entrada.txt ciudades.txt [--wrap] [--min=N]
 *
 * ciudades.txt puede ser:
 *   - una lista simple, un nombre por línea, o
 *   - un volcado de GeoNames (cities500.txt, FR.txt...), separado por tabuladores.
 *
 * Salida: las ciudades encontradas en una sola línea, en orden de aparición en la sopa
 * (fila, columna) y sin repetir. Por stderr se muestra el detalle de cada hallazgo.
 */
public class Donald {

    static int MIN_LEN = 5;   // se puede subir con --min=N para evitar falsos positivos

    // 8 direcciones: derecha, izquierda, abajo, arriba y las 4 diagonales
    static final int[] DR = {0, 0, 1, -1, 1, -1, 1, -1};
    static final int[] DC = {1, -1, 0, 0, 1, -1, -1, 1};
    static final String[] DIR_NAME = {"der", "izq", "abajo", "arriba", "diag-abajo-der", "diag-arriba-izq", "diag-abajo-izq", "diag-arriba-der"};

    // ---------- Trie: permite buscar miles de ciudades a la vez ----------
    static class Node {
        Node[] next = new Node[26];
        String word;            // != null si aquí termina una ciudad
    }

    static Node root = new Node();
    static int maxLen = 0;

    static void insert(String w) {
        Node cur = root;
        for (char ch : w.toCharArray()) {
            int i = ch - 'a';
            if (cur.next[i] == null) cur.next[i] = new Node();
            cur = cur.next[i];
        }
        cur.word = w;
        maxLen = Math.max(maxLen, w.length());
    }

    /** Quita tildes, pasa a minúsculas y deja solo letras a-z ("Châtellerault" -> "chatellerault"). */
    static String normalize(String s) {
        String t = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")           // tildes, diéresis, cedillas...
                .toLowerCase(Locale.ROOT)
                .replace("ß", "ss").replace("æ", "ae").replace("œ", "oe").replace("ø", "o");
        return t.replaceAll("[^a-z]", "");          // fuera espacios, guiones, apóstrofos...
    }

    // ---------- Lectura de ficheros ----------
    static char[][] readGrid(String path) throws IOException {
        List<char[]> rows = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)) {
            String letters = line.replaceAll("\\s+", "").toLowerCase(Locale.ROOT);
            if (!letters.isEmpty()) rows.add(letters.toCharArray());
        }
        return rows.toArray(new char[0][]);
    }

    static int readCities(String path) throws IOException {
        Set<String> seen = new HashSet<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> names = new ArrayList<>();
                if (line.contains("\t")) {               // formato GeoNames
                    String[] f = line.split("\t");
                    if (f.length > 1) names.add(f[1]);   // name
                    if (f.length > 2) names.add(f[2]);   // asciiname
                } else {
                    names.add(line);                     // lista simple
                }
                for (String n : names) {
                    String w = normalize(n);
                    if (w.length() >= MIN_LEN && seen.add(w)) insert(w);
                }
            }
        }
        return seen.size();
    }

    // ---------- Búsqueda ----------
    record Hit(String word, int row, int col, int dir) {}

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Uso: java Donald entrada.txt ciudades.txt [--wrap] [--min=N]");
            return;
        }
        boolean wrap = false;
        for (int i = 2; i < args.length; i++) {
            if (args[i].equals("--wrap")) wrap = true;
            else if (args[i].startsWith("--min=")) MIN_LEN = Integer.parseInt(args[i].substring(6));
        }

        char[][] g = readGrid(args[0]);
        int n = g.length, m = g[0].length;
        int total = readCities(args[1]);
        System.err.printf("Sopa %dx%d, %d ciudades (>= %d letras), wrap=%b%n", n, m, total, MIN_LEN, wrap);

        List<Hit> hits = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                for (int d = 0; d < 8; d++) {
                    Node cur = root;
                    // Avanzamos letra a letra por el trie mientras el camino exista
                    for (int k = 0; k < maxLen; k++) {
                        int rr = r + DR[d] * k, cc = c + DC[d] * k;
                        if (wrap) {
                            rr = Math.floorMod(rr, n);
                            cc = Math.floorMod(cc, m);
                            if (k > 0 && rr == r && cc == c) break; // ha dado la vuelta entera
                        } else if (rr < 0 || rr >= n || cc < 0 || cc >= m) {
                            break;
                        }
                        int idx = g[rr][cc] - 'a';
                        if (idx < 0 || idx >= 26 || (cur = cur.next[idx]) == null) break;
                        if (cur.word != null) hits.add(new Hit(cur.word, r, c, d));
                    }
                }
            }
        }

        // Si una ciudad está contenida en otra encontrada en el mismo sitio
        // (p.ej. "chapelle" dentro de "chapelleux"), nos quedamos solo con la más larga.
        Map<String, Hit> best = new HashMap<>();
        for (Hit h : hits) {
            String key = h.row() + "," + h.col() + "," + h.dir();
            Hit prev = best.get(key);
            if (prev == null || h.word().length() > prev.word().length()) best.put(key, h);
        }

        List<Hit> result = new ArrayList<>(best.values());
        result.sort(Comparator.comparingInt(Hit::row).thenComparingInt(Hit::col));

        LinkedHashSet<String> unique = new LinkedHashSet<>();
        for (Hit h : result) {
            System.err.printf("  %-25s fila %3d col %3d %s%n", h.word(), h.row(), h.col(), DIR_NAME[h.dir()]);
            unique.add(h.word());
        }
        System.err.println("Total: " + unique.size() + " ciudades");

        // Respuesta: una sola línea
        System.out.println(String.join(" ", unique));
    }
}
