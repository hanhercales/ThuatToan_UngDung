package buoi7._21_LongestRepeatedSubstring;

import buoi7.SuffixArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LongestRepeatedSubstring {
    private LongestRepeatedSubstring() {
    }

    public static String lrs(String text) {
        int n = text.length();
        SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < n; i++) {
            int length = sa.lcp(i);
            if (length > lrs.length()) {
                // lrs = sa.select(i).substring(0, length);
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        return lrs;
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
        String text = readFile("src/buoi7/file.cpp");

        String result = lrs(text);
        System.out.println("Longest Repeated Substring:");
        System.out.println(result);
    }
}
