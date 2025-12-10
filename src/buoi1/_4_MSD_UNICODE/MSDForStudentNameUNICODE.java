package buoi1._4_MSD_UNICODE;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class MSDForStudentNameUNICODE {
    public static void main(String[] args) {
        // 1. Cấu hình Console Output sang UTF-8
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String inputPath = "src/buoi1/Students.docx";

        // 2. Đọc file .docx
        List<String> rawLines = readDocxFile(inputPath);

        if (rawLines.isEmpty()) {
            System.out.println("File rỗng, không tồn tại hoặc lỗi thư viện.");
            System.out.println("Vui lòng kiểm tra lại đường dẫn và thư viện Apache POI.");
            return;
        }

        int n = rawLines.size();
        String[] sortableArray = new String[n];

        // 3. Decorate
        for (int i = 0; i < n; i++) {
            String line = rawLines.get(i);
            String name = extractFirstName(line);
            sortableArray[i] = name + " " + line;
        }

        // 4. Gọi thuật toán MSDUNICODE
        try {
            MSDUNICODE.sort(sortableArray);
        } catch (IllegalArgumentException e) {
            System.err.println("LỖI DỮ LIỆU: " + e.getMessage());
            System.err.println("Hãy thêm ký tự này vào VNAlphabet.VIETNAMESE");
            return;
        } catch (NoClassDefFoundError e) {
            System.err.println("LỖI THƯ VIỆN: Không tìm thấy class MSDUNICODE hoặc VNAlphabet.");
            return;
        }

        // 5. Khôi phục và in kết quả
        System.out.println("--- Kết quả ---");
        for (String item : sortableArray) {
            int firstSpace = item.indexOf(' ');
            if (firstSpace != -1) {
                System.out.println(item.substring(firstSpace + 1));
            } else {
                System.out.println(item);
            }
        }
    }

    private static List<String> readDocxFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("Lỗi: Không tìm thấy file tại " + file.getAbsolutePath());
            return lines;
        }

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis)) {

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                String text = para.getText();
                if (text != null && !text.trim().isEmpty()) {
                    lines.add(text.trim());
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi IO khi đọc file: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
            System.err.println("LỖI THIẾU THƯ VIỆN: Dự án của bạn thiếu file .jar của Apache POI.");
            System.err.println("Chi tiết: " + e.getMessage());
        }
        return lines;
    }

    private static String extractFirstName(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length >= 2) {
            return parts[parts.length - 2];
        }
        return parts.length > 0 ? parts[0] : "";
    }
}
