package buoi8.lzw._25_26_LZW;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class LZW {
    private static final int R = 65535;  // MAX 16-bit
    private static final int W = 16;
    private static final int L = 1 << W; // still 65536

    public static void compress() {
        String input = BinaryStdIn.readString();
        HashMap<String, Integer> st = new HashMap<>();

        // since TST is not balanced, it would be better to insert in a different order
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);

        int code = R + 1;  // R is codeword for EOF

        while (!input.isEmpty()) {

            // Tìm prefix dài nhất có trong bảng mã
            int t = 1;
            while (t <= input.length() && st.containsKey(input.substring(0, t))) {
                t++;
            }

            // Chuỗi khớp dài nhất là input[0..t-2]
            String s = input.substring(0, t - 1);

            // Ghi mã của s
            BinaryStdOut.write(st.get(s), W);

            // Nếu chưa hết input, thêm s + ký tự tiếp theo
            if (t <= input.length() && code < L) {
                st.put(input.substring(0, t), code++);
            }

            // Bỏ qua s trong input
            input = input.substring(s.length());
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String basePath = "src/buoi8/lzw/";
        String inputFileName = basePath + "tinytinyTale.txt"; // File text gốc
        String compressedFileName = basePath + "dulieu_nen.bin"; // File kết quả nén

        boolean toCompress = true;
        if (toCompress) {
            // Nén
            System.setIn(new FileInputStream(inputFileName));
            System.setOut(new PrintStream(new FileOutputStream(compressedFileName)));
            compress();
            BinaryStdIn.close();
            BinaryStdOut.close();
        } else {
            // Giải nén
            System.setIn(new FileInputStream(compressedFileName));
            System.setOut(System.out);
            expand();
            BinaryStdIn.close();
            BinaryStdOut.close();
        }
    }
}
