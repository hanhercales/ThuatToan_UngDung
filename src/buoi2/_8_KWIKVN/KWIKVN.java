package buoi2._8_KWIKVN;

import buoi2._7_SuffixArrayVN.SuffixArrayVN;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KWIKVN {
    private KWIKVN() { }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String fileName;
        int context;

        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "src/buoi1/VNstudents.txt";
            System.out.println("Không có tham số dòng lệnh. Sử dụng file mặc định: " + fileName);
        }

        if (args.length > 1) {
            context = Integer.parseInt(args[1]);
        } else {
            context = 20;
        }

        String text = readAllText(fileName);

        if (text == null || text.isEmpty()) {
            System.out.println("Lỗi: File rỗng hoặc không tồn tại.");
            return;
        }

        SuffixArrayVN sa = new SuffixArrayVN(text);

        System.out.println("------------------------------------------------");
        System.out.println("Sẵn sàng tìm kiếm. Nhập từ khóa (Gõ 'exit' để thoát):");

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                System.out.print("> ");
                String query = scanner.nextLine().trim();

                if (query.equalsIgnoreCase("exit")) break;
                if (query.isEmpty()) continue;

                int count = 0;
                int n = text.length();

                // Tìm kiếm dựa trên Rank trong Suffix Array
                for (int i = sa.rank(query); i < n; i++) {
                    int from1 = sa.index(i);
                    int to1 = Math.min(n, from1 + query.length());

                    // Kiểm tra hậu tố có bắt đầu bằng query không
                    if (!query.equals(text.substring(from1, to1))) {
                        break;
                    }

                    // Xác định ngữ cảnh (Context)
                    int from2 = Math.max(0, sa.index(i) - context);
                    int to2 = Math.min(n, sa.index(i) + query.length() + context);

                    // In kết quả
                    String result = text.substring(from2, to2).replace("\n", " ");
                    System.out.println("..." + result + "...");
                    count++;
                }

                if (count == 0) {
                    System.out.println("(Không tìm thấy kết quả)");
                } else {
                    System.out.println("=> Tìm thấy " + count + " kết quả.");
                }
            }
        }
    }

    private static String readAllText(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File không tồn tại tại đường dẫn: " + file.getAbsolutePath());
            return null;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sb.append(line.trim()).append(" ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return sb.toString().replaceAll("\\s+", " ").trim();
    }
}
