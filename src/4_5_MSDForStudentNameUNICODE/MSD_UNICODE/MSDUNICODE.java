package MSD_UNICODE;

import VNAlphabet.VNAlphabet;

public class MSDUNICODE {
    private static final int R = VNAlphabet.VIETNAMESE.radix();
    private static final int CUTOFF = 15;

    private MSDUNICODE() { }

    public static void sort(String[] a) {
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux);
    }

    private static int charAt(String s, int d) {
        if (d >= s.length()) return -1;
        char c = s.charAt(d);
        return VNAlphabet.VIETNAMESE.toIndex(c);
    }

    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];

        // 1. Đếm tần số
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c + 2]++;
        }

        // 2. Cộng dồn
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // 3. Phân phối
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }

        // 4. Copy ngược lại
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // 5. Đệ quy
        for (int r = 0; r < R; r++) {
            if (count[r + 1] > count[r]) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }
        }
    }

    // Insertion sort tùy chỉnh dùng charAt của VNAlphabet
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            int i1 = charAt(v, i);
            int i2 = charAt(w, i);
            if (i1 < i2) return true;
            if (i1 > i2) return false;
        }
        return v.length() < w.length();
    }
}
