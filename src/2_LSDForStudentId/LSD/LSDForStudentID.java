package LSD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LSDForStudentID {
    public static void sort(String[] data, int[] keys) {
        final int BITS = 32;                 // 32-bit integers
        final int BITS_PER_BYTE = 8;
        final int R = 1 << BITS_PER_BYTE;    // 256
        final int MASK = R - 1;              // 0xFF
        final int w = BITS / BITS_PER_BYTE;  // 4 bytes

        int n = keys.length;
        int[] auxKeys = new int[n];          // Mảng phụ cho Key
        String[] auxData = new String[n];    // Mảng phụ cho Data (Sinh viên)

        for (int d = 0; d < w; d++) {

            // 1. Compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++) {
                int c = (keys[i] >> BITS_PER_BYTE*d) & MASK;
                count[c + 1]++;
            }

            // 2. Compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // 3. Handle sign bit
            if (d == w-1) {
                int shift1 = count[R] - count[R/2];
                int shift2 = count[R/2];
                for (int r = 0; r < R/2; r++)
                    count[r] += shift1;
                for (int r = R/2; r < R; r++)
                    count[r] -= shift2;
            }

            // 4. Move data
            for (int i = 0; i < n; i++) {
                int c = (keys[i] >> BITS_PER_BYTE*d) & MASK;
                int index = count[c]++;

                auxKeys[index] = keys[i];       // Chuyển Key
                auxData[index] = data[i];       // Chuyển Data (Tên SV) theo Key
            }

            // 5. Copy back
            for (int i = 0; i < n; i++) {
                keys[i] = auxKeys[i];
                data[i] = auxData[i];
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "TestCase/VNstudents.txt";

        List<String> rawLines = new ArrayList<>();
        List<Integer> extractedIds = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 1) {
                        String idStr = parts[parts.length - 1];

                        try {
                            int id = Integer.parseInt(idStr.replaceAll("[^0-9]", ""));

                            rawLines.add(line.trim());
                            extractedIds.add(id);
                        } catch (NumberFormatException e) {
                            System.err.println("Không thể lấy mã SV từ dòng: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không tìm thấy file " + filePath + ", tạo dữ liệu mẫu...");
        }

        String[] students = rawLines.toArray(new String[0]);
        int[] ids = extractedIds.stream().mapToInt(i -> i).toArray();

        System.out.println("--- Danh sách ban đầu ---");
        for (String s : students) System.out.println(s);

        sort(students, ids);

        System.out.println("\n--- Danh sách sau khi sắp xếp theo Mã SV ---");
        for (String s : students) System.out.println(s);
    }
}
