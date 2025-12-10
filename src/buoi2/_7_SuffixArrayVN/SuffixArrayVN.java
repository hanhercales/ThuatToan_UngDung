package buoi2._7_SuffixArrayVN;

import java.io.*;
import java.nio.charset.StandardCharsets;
import buoi1._1_VNAlphabet.VNAlphabet;

public class SuffixArrayVN {
    private static final int CUTOFF = 5;

    private final char[] text;
    private final int[] index;   // Mảng hậu tố: index[i] = vị trí bắt đầu của hậu tố nhỏ thứ i
    private final int n;         // độ dài văn bản

    public SuffixArrayVN(String text) {
        n = text.length();
        text = text + '\0';
        this.text = text.toCharArray();
        this.index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        sort(0, n - 1, 0);
    }

    private int getVal(char c) {
        if (c == '\0') return -1;
        try {
            return VNAlphabet.VIETNAMESE.toIndex(c);
        } catch (IllegalArgumentException e) {
            return Character.MAX_VALUE + c;
        }
    }

    private void sort(int lo, int hi, int d) {
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        // Lấy giá trị so sánh từ VNAlphabet
        int v = getVal(text[index[lo] + d]);

        int i = lo + 1;
        while (i <= gt) {
            int t = getVal(text[index[i] + d]);
            if      (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else            i++;
        }

        sort(lo, lt - 1, d);
        if (v >= 0) sort(lt, gt, d + 1); // Chỉ sort tiếp nếu chưa gặp Sentinel
        sort(gt + 1, hi, d);
    }

    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(index[j], index[j - 1], d); j--)
                exch(j, j - 1);
    }

    private boolean less(int i, int j, int d) {
        if (i == j) return false;
        i = i + d;
        j = j + d;
        while (i < n + 1 && j < n + 1) {
            int vi = getVal(text[i]);
            int vj = getVal(text[j]);
            if (vi < vj) return true;
            if (vi > vj) return false;
            i++;
            j++;
        }
        return i > j;
    }

    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    public int length() { return n; }

    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return index[i];
    }

    public String select(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return new String(text, index[i], n - index[i]);
    }

    public int rank(String query) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, index[mid]);
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    private int compare(String query, int i) {
        int m = query.length();
        int j = 0;
        while (i < n && j < m) {
            int valQuery = getVal(query.charAt(j));
            int valText = getVal(text[i]);

            if (valQuery != valText) return valQuery - valText;
            i++;
            j++;
        }
        if (i < n) return -1;
        if (j < m) return +1;
        return 0;
    }

    public int lcp(int i) {
        if (i < 1 || i >= n) throw new IllegalArgumentException();
        return lcp(index[i], index[i - 1]);
    }

    private int lcp(int i, int j) {
        int length = 0;
        while (i < n && j < n) {
            if (getVal(text[i]) != getVal(text[j])) return length; // So sánh theo VNAlphabet
            i++;
            j++;
            length++;
        }
        return length;
    }

    public static void main(String[] args) {
        // Cấu hình Output UTF-8
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) { e.printStackTrace(); }

        String inputPath = "TestCase/VNstudents.txt";
        File file = new File(inputPath);

        String content = "";

        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!line.trim().isEmpty()) {
                        sb.append(line.trim()).append(" ");
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
            content = sb.toString().trim();
        }

        // Xây dựng Suffix Array
        SuffixArrayVN sa = new SuffixArrayVN(content);

        System.out.println("\n--- MẢNG HẬU TỐ (SUFFIX ARRAY) TIẾNG VIỆT ---");
        System.out.printf("%-4s %-4s %-4s %s\n", "i", "ind", "lcp", "suffix");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < sa.length(); i++) {
            int index = sa.index(i);
            String suffix = sa.select(i);
            // Cắt ngắn suffix để in cho gọn
            String displaySuffix = suffix.length() > 50 ? suffix.substring(0, 50) + "..." : suffix;

            int lcp = (i == 0) ? 0 : sa.lcp(i);
            String lcpStr = (i == 0) ? "-" : String.valueOf(lcp);

            System.out.printf("%-4d %-4d %-4s %s\n", i, index, lcpStr, displaySuffix);
        }
    }
}
