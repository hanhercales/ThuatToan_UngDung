package buoi4._15_16_KMP_VN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KMPfile {
    private final int R; // the radix
    private int[][] dfa; // the KMP automoton

    private char[] pattern; // either the character array for the pattern
    private String pat; // or the pattern string

    public KMPfile(String pat) {
        this.R = 65536;
        this.pat = pat;

        // build DFA from pattern
        int m = pat.length();
        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x]; // Copy mismatch cases.
            dfa[pat.charAt(j)][j] = j + 1; // Set match case.
            x = dfa[pat.charAt(j)][x]; // Update restart state.
        }
    }

    public KMPfile(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        // build DFA from pattern
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x]; // Copy mismatch cases.
            dfa[pattern[j]][j] = j + 1; // Set match case.
            x = dfa[pattern[j]][x]; // Update restart state.
        }
    }

    public int search(String txt) {

        // simulate operation of DFA on text
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m)
            return i - m; // found
        return n; // not found
    }

    public int search(char[] text) {

        // simulate operation of DFA on text
        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }
        if (j == m)
            return i - m; // found
        return n; // not found
    }

    public static void main(String[] args) throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader("src/buoi4/MauVanBan.txt"));

        StringBuilder patContent = new StringBuilder();
        while (fr.ready()) {
            String line = fr.readLine().trim();
            patContent.append(line).append(" ");
        }
        fr.close();

        KMPfile kmp = new KMPfile(patContent.toString());

        fr = new BufferedReader(new FileReader("src/buoi4/VanBan.txt"));
        StringBuilder content = new StringBuilder();
        while (fr.ready()) {
            String line = fr.readLine().trim();
            content.append(line).append(" ");
        }
        fr.close();

        int offset = kmp.search(content.toString());
        if (offset < content.length())
            System.out.println("Pattern found at index " + offset);
        else
            System.out.println("Pattern not found");
    }
}
