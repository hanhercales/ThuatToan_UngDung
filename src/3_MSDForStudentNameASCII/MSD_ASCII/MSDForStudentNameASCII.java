package MSD_ASCII;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MSDForStudentNameASCII {
    public static void main(String[] args) {
        String inputPath = "TestCase/students.txt";
        List<String> originalLines = new ArrayList<>();

        File file = new File(inputPath);
        if (!file.exists()) {
            System.err.println("File không tồn tại: " + inputPath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    originalLines.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int n = originalLines.size();
        String[] sortableArray = new String[n];

        for (int i = 0; i < n; i++) {
            String line = originalLines.get(i);
            String name = extractFirstName(line);

            sortableArray[i] = name + "\0" + line;
        }

        MSD.sort(sortableArray);

        System.out.println("--- Kết quả sau khi sắp xếp theo Tên ---");
        for (String item : sortableArray) {
            int separatorIndex = item.indexOf('\0');
            String originalData = item.substring(separatorIndex + 1);
            System.out.println(originalData);
        }
    }

    private static String extractFirstName(String line) {
        if (line == null || line.isEmpty()) return "";

        String[] parts = line.trim().split("\\s+");

        if (parts.length >= 2) {
            return parts[parts.length - 2];
        }
        return parts[0];
    }
}
