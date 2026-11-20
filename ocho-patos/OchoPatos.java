import java.nio.file.*;
import java.util.*;

public class OchoPatos {

    public static void main(String[] args) throws Exception {
        // 1) Leer los 100 números
        String txt = Files.readString(Path.of(args.length > 0 ? args[0] : "input.txt"));
        long[] nums = Arrays.stream(txt.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();

        // 2) Buscar qué patos dicen la verdad (fuerza bruta, 256 combinaciones)
        boolean[] t = null;
        for (int mask = 0; mask < 256; mask++) {
            boolean[] c = new boolean[9]; // índices 1..8
            for (int i = 1; i <= 8; i++) c[i] = ((mask >> (i - 1)) & 1) == 1;
            if (consistente(c)) { t = c; break; }
        }

        StringBuilder sb = new StringBuilder("Veraces:");
        for (int i = 1; i <= 8; i++) if (t[i]) sb.append(" ").append(i);
        System.err.println(sb);

        // 3) Aplicar las reglas de los veraces, en orden
        for (int pato = 1; pato <= 8; pato++) {
            if (!t[pato]) continue;
            for (int i = 0; i < nums.length; i++) nums[i] = regla(pato, nums[i]);
        }

        // 4) Sumar
        long suma = 0;
        for (long n : nums) suma += n;
        System.out.println(suma);
    }

    static boolean consistente(boolean[] t) {
        int veraces = 0, pares = 0, impares = 0, sumaIds = 0;
        for (int i = 1; i <= 8; i++) if (t[i]) {
            veraces++; sumaIds += i;
            if (i % 2 == 0) pares++; else impares++;
        }
        boolean[] a = new boolean[9];
        a[1] = !t[4] && !t[5] && !t[6] && !t[7] && !t[8];
        a[2] = t[5] != t[7];
        a[3] = veraces == 4;
        a[4] = t[3] != t[4];
        a[5] = !t[7] && !t[8];
        a[6] = pares > impares;
        a[7] = !t[2] || !t[3];
        a[8] = sumaIds % 3 == 0;
        // veraz => afirmación cierta; mentiroso => afirmación falsa
        for (int i = 1; i <= 8; i++) if (a[i] != t[i]) return false;
        return true;
    }

    static long regla(int pato, long n) {
        switch (pato) {
            case 1: return (n % 2 != 0) ? n * 3 : n;                       // impares x3
            case 2: return (n < 10) ? n + 5 : n;                            // <10 → +5
            case 3: return (n % 2 == 0) ? n / 2 : n;                        // pares /2
            case 4: return (n >= 10 && n <= 99) ? n / 10 + n % 10 : n;      // suma de cifras
            case 5: return ((Math.abs(n) % 10) % 2 == 0) ? n * 2 : n;       // acaba en par → x2
            case 6: return (n % 5 == 0) ? 0 : n;                            // múltiplo de 5 → 0
            case 7: return n + 1;                                           // +1
            case 8: return (n < 0) ? 0 : n;                                 // negativos → 0
            default: return n;
        }
    }
}