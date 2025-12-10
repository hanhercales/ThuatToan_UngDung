package buoi3._10_11_TSTForRate;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AvgPoint {
    public static void main(String[] args) {
        TST<Double> tst = new TST<>();
        File file = new File("src/buoi3/student.csv");
        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            boolean isFirstLine = true;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                if (isFirstLine && line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                    isFirstLine = false;
                }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[1].trim();
                    try {
                        double point = Double.parseDouble(parts[2].trim());
                        tst.put(name, point);
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng điểm số ở dòng: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy file 'src/buoi3/student.csv'.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Scanner consoleScanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("Nhập họ đệm");
        System.out.println("Nhập 'exit' để thoát chương trình.");
        while (true) {
            System.out.print("Tra cứu > ");
            String prefix = consoleScanner.nextLine().trim();
            if (prefix.equalsIgnoreCase("exit")) {
                break;
            }
            if (prefix.isEmpty()) continue;
            Iterable<String> matchingNames = tst.keysWithPrefix(prefix);
            double totalScore = 0;
            int count = 0;
            boolean found = false;
            for (String name : matchingNames) {
                found = true;
                Double score = tst.get(name); // Lấy điểm từ TST
                if (score != null) {
                    System.out.printf("  - %-25s : %.2f\n", name, score);
                    totalScore += score;
                    count++;
                }
            }
            if (!found) {
                System.out.println("  (Không tìm thấy sinh viên nào)");
            } else {
                double avg = totalScore / count;
                System.out.printf("=> Điểm trung bình nhóm '%s' (%d sv): %.2f\n\n", prefix, count, avg);
            }
        }
        consoleScanner.close();
    }
}
