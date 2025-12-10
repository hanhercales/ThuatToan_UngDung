package buoi7._22_LongestCommonSubstring;

import buoi7.SuffixArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LongestCommonSubstring {
    private LongestCommonSubstring() {
    }

    private static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }

    private static int compare(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.charAt(p + i) - t.charAt(q + i);
        }
        if (s.length() - p < t.length() - q) return -1;
        else if (s.length() - p > t.length() - q) return +1;
        else return 0;
    }

    public static String lcs(String s, String t) {
        SuffixArray suffix1 = new SuffixArray(s);
        SuffixArray suffix2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes
        String lcs = "";
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffix1.index(i);
            int q = suffix2.index(j);
            String x = lcp(s, p, t, q);
            if (x.length() > lcs.length()) lcs = x;
            if (compare(s, p, t, q) < 0) i++;
            else j++;
        }
        return lcs;
    }

    private static String readFile(String filePath) throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader(filePath));
        StringBuilder fileContent = new StringBuilder();

        while (fr.ready()) {
            String line = fr.readLine().trim();
            fileContent.append(line).append(" ");
        }
        fr.close();
        return fileContent.toString();
    }

    public static void main(String[] args) throws IOException {
        String text1 = readFile("src/buoi7/baocao1.txt");
        String text2 = readFile("src/buoi7/baocao2.txt");

        String result = lcs(text1, text2);

        System.out.println("Longest Common Substring:");
        System.out.println(result);
    }
}
