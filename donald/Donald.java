import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Reto "Donald" - Unicode 2026 (CodeLab)
 *
 * Encuentra nombres de ciudades escondidos en una sopa de letras SIN usar ningún listado.
 *
 * IDEA
 *   La sopa es casi toda ruido: letras aleatorias uniformes (cada una con prob. 1/26).
 *   Las ciudades, en cambio, son texto "humano": después de una 'q' casi siempre va una 'u',
 *   después de una consonante suele ir una vocal, "ll", "ch", "nt"... son frecuentes, y "qz", "xj"...
 *   casi no existen.
 *
 *   Así que medimos, para cada par de letras seguidas (bigrama), cuánto más probable es en un
 *   idioma real que en ruido aleatorio:
 *
 *        puntos(a,b) = log( P_idioma(b | a) / (1/26) )
 *
 *   positivo  -> el par "suena a palabra"
 *   negativo  -> el par "suena a ruido"
 *
 *   Recorremos cada línea de la sopa en las 8 direcciones sumando puntos. Un tramo cuya suma
 *   es muy alta es casi seguro una ciudad escondida (es el problema clásico del
 *   "subarray de suma máxima", algoritmo de Kadane).
 *
 *   Las probabilidades P_idioma se aprenden de un texto corto de ejemplo en varios idiomas
 *   que va incluido abajo. No contiene ninguna lista de ciudades: solo frases normales.
 *
 *   Enlace con el enunciado:
 *     - "ciudades con 4 o menos letras... las araña": las palabras cortas no se pueden
 *       distinguir del ruido, así que solo buscamos tramos largos (MIN_LEN).
 *     - "nombres separados... los encaja en uno solo": los nombres compuestos van juntos,
 *       y como seguimos sumando mientras "suene a palabra", salen enteros.
 *     - "da vueltas": se busca en las 8 direcciones.
 *
 * USO
 *   javac Donald.java
 *   java Donald entrada.txt [umbral] [minLetras]
 *
 *   Por defecto umbral = 12 y minLetras = 8. Si salen cosas raras, sube el umbral;
 *   si salen pocas ciudades, bájalo.
 */
public class Donald {

    // Texto de entrenamiento: frases normales en varios idiomas (NO es un listado de ciudades).
    static final String CORPUS = """
        el pato donald vive en una casa pequena junto al rio y cada manana sale a pasear por la ciudad
        con sus sobrinos mientras el sol brilla sobre las montanas del norte y los campos verdes del valle
        la ville est situee au bord de la mer entre les collines et la foret elle compte plusieurs eglises
        une grande place centrale un marche couvert et un chateau construit au moyen age par les seigneurs
        de la region
        the city lies on the northern bank of the river and is known for its old town hall the cathedral
        the harbour and the university founded in the sixteenth century by the local bishop and merchants
        la citta si trova in una valle circondata da montagne e boschi il centro storico conserva chiese
        palazzi e una piazza dove ogni settimana si tiene il mercato dei prodotti della campagna
        a cidade fica perto do mar e tem uma praia grande uma igreja antiga e um porto onde chegam os
        barcos de pesca todas as manhas
        die stadt liegt am ufer des flusses und hat eine schoene altstadt mit kirchen brunnen und einem
        rathaus aus dem mittelalter
        santa maria san jose saint pierre sainte anne villa nueva puerto nuevo monte alto valle verde
        rio grande castel nuovo bourg neuf
        """;

    static final int[] DR = {0, 0, 1, -1, 1, -1, 1, -1};
    static final int[] DC = {1, -1, 0, 0, 1, -1, -1, 1};
    static final String[] DIR = {"der", "izq", "abajo", "arriba",
                                 "diag-abajo-der", "diag-arriba-izq", "diag-abajo-izq", "diag-arriba-der"};

    static double[][] score = new double[26][26];

    record Hit(String word, double score, int row, int col, int dir) {}

    /** 1) Aprende del texto cuánto "suena a idioma" cada par de letras. */
    static void train() {
        int[][] pair = new int[26][26];
        int[] from = new int[26];
        for (String w : CORPUS.split("[^a-z]+")) {
            for (int i = 0; i + 1 < w.length(); i++) {
                pair[w.charAt(i) - 'a'][w.charAt(i + 1) - 'a']++;
                from[w.charAt(i) - 'a']++;
            }
        }
        double alpha = 0.3; // suavizado: los pares que no vimos nunca no son "imposibles"
        for (int a = 0; a < 26; a++)
            for (int b = 0; b < 26; b++) {
                double pIdioma = (pair[a][b] + alpha) / (from[a] + 26 * alpha);
                score[a][b] = Math.log(pIdioma / (1.0 / 26)); // comparado con ruido uniforme
            }
    }

    static char[][] readGrid(String path) throws IOException {
        List<char[]> rows = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)) {
            String s = line.replaceAll("[^a-zA-Z]", "").toLowerCase(Locale.ROOT);
            if (!s.isEmpty()) rows.add(s.toCharArray());
        }
        return rows.toArray(new char[0][]);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Uso: java Donald entrada.txt [umbral=12] [minLetras=8]");
            return;
        }
        double threshold = args.length > 1 ? Double.parseDouble(args[1]) : 12.0;
        int minLen = args.length > 2 ? Integer.parseInt(args[2]) : 8;

        train();
        char[][] g = readGrid(args[0]);
        int n = g.length, m = g[0].length;

        List<Hit> hits = new ArrayList<>();

        // 2) Recorremos cada línea completa de la sopa en cada una de las 8 direcciones.
        //    Una línea empieza en una casilla del borde desde la que se puede avanzar en esa dirección.
        for (int d = 0; d < 8; d++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    int pr = r - DR[d], pc = c - DC[d];
                    boolean isStart = pr < 0 || pr >= n || pc < 0 || pc >= m;
                    if (isStart) scanLine(g, r, c, d, hits);
                }
            }
        }

        // 3) Filtramos: puntuación alta y longitud suficiente.
        hits.removeIf(h -> h.score() < threshold || h.word().length() < minLen);
        hits.sort((a, b) -> Double.compare(b.score(), a.score()));

        // 4) Cada ciudad aparece dos veces (leída del derecho y del revés). Nos quedamos con la mejor.
        List<Hit> result = new ArrayList<>();
        for (Hit h : hits) {
            String rev = new StringBuilder(h.word()).reverse().toString();
            boolean dup = false;
            for (Hit k : result) if (overlaps(rev, k.word()) || overlaps(h.word(), k.word())) { dup = true; break; }
            if (!dup) result.add(h);
        }

        List<String> words = new ArrayList<>();
        for (Hit h : result) {
            System.err.printf("%6.1f  %-45s fila %3d col %3d %s%n",
                    h.score(), h.word(), h.row(), h.col(), DIR[h.dir()]);
            words.add(h.word());
        }
        System.err.println("Encontradas: " + words.size());
        System.out.println(String.join(" ", words)); // respuesta en una sola línea
    }

    /**
     * Kadane sobre una línea: vamos sumando puntos de cada par de letras.
     * Si la suma cae a 0 o menos, el tramo era ruido y empezamos de nuevo.
     * Cuando un tramo termina, guardamos su mejor punto (donde la suma fue máxima).
     */
    static void scanLine(char[][] g, int r0, int c0, int d, List<Hit> out) {
        int n = g.length, m = g[0].length;
        StringBuilder line = new StringBuilder();
        for (int r = r0, c = c0; r >= 0 && r < n && c >= 0 && c < m; r += DR[d], c += DC[d]) line.append(g[r][c]);

        double cur = 0, best = 0;
        int start = 0, bestEnd = 0;
        for (int i = 1; i <= line.length(); i++) {
            double v = i < line.length() ? score[line.charAt(i - 1) - 'a'][line.charAt(i) - 'a'] : -1e9;
            if (cur + v <= 0) {
                if (best > 0) {
                    out.add(new Hit(line.substring(start, bestEnd + 1), best,
                            r0 + DR[d] * start, c0 + DC[d] * start, d));
                }
                cur = 0; best = 0; start = i;
                continue;
            }
            cur += v;
            if (cur > best) { best = cur; bestEnd = i; }
        }
    }

    /** ¿Comparten las dos cadenas un trozo de al menos 8 letras? */
    static boolean overlaps(String a, String b) {
        for (int i = 0; i + 8 <= a.length(); i++) if (b.contains(a.substring(i, i + 8))) return true;
        return false;
    }
}
