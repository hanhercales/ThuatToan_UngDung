package buoi2._6_Quick3String;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Quick3StringForStudent {
    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String inputPath = "src/buoi1/students.txt";

        List<String> rawLines = readTextFile(inputPath);
        if (rawLines.isEmpty()) {
            System.out.println("File rỗng hoặc không tìm thấy: " + inputPath);
            return;
        }

        int n = rawLines.size();
        String[] sortableArray = new String[n];

        for (int i = 0; i < n; i++) {
            String line = rawLines.get(i);
            String name = extractFirstName(line);
            sortableArray[i] = name + "\0" + line;
        }

        Quick3string.sort(sortableArray);

        System.out.println("--- Kết quả sắp xếp theo Tên ---");
        for (String item : sortableArray) {
            int separatorIndex = item.indexOf('\0');
            if (separatorIndex != -1) {
                System.out.println(item.substring(separatorIndex + 1));
            } else {
                System.out.println(item);
            }
        }
    }

    private static List<String> readTextFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static String extractFirstName(String line) {
        if (line == null || line.isEmpty()) return "";

        String[] parts = line.trim().split("\\s+");

        if (parts.length >= 2) {
            return parts[parts.length - 2];
        }
        return parts.length > 0 ? parts[0] : "";
    }
}
