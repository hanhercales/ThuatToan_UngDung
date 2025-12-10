package buoi1._1_VNAlphabet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VNAlphabet {
    public static final VNAlphabet BINARY = new VNAlphabet("01");
    public static final VNAlphabet DECIMAL = new VNAlphabet("0123456789");
    public static final VNAlphabet DNA = new VNAlphabet("ACGT");
    public static final VNAlphabet LOWERCASE = new VNAlphabet("abcdefghijklmnopqrstuvwxyz");
    public static final VNAlphabet UPPERCASE = new VNAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final VNAlphabet PROTEIN = new VNAlphabet("ACDEFGHIKLMNPQRSTVWY");
    public static final VNAlphabet BASE64 = new VNAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    public static final VNAlphabet ASCII = new VNAlphabet(128);
    public static final VNAlphabet EXTENDED_ASCII = new VNAlphabet(256);
    public static final VNAlphabet UNICODE16 = new VNAlphabet(65536);
    public static final VNAlphabet VIETNAMESE = new VNAlphabet(
            " " + // Dấu cách (Space) xếp đầu tiên để tách tên đệm/tên chính xác
                    "AaÀàÁáẢảÃãẠạ" +
                    "ĂăẰằẮắẲẳẴẵẶặ" +
                    "ÂâẦầẤấẨẩẪẫẬậ" +
                    "BbCcDdĐđ" +
                    "EeÈèÉéẺẻẼẽẸẹ" +
                    "ÊêỀềẾếỂểỄễỆệ" +
                    "GgHh" +
                    "IiÌìÍíỈỉĨĩỊị" +
                    "KkLlMmNn" +
                    "OoÒòÓóỎỏÕõỌọ" +
                    "ÔôỒồỐốỔổỖỗỘộ" +
                    "ƠơỜờỚớỞởỠỡỢợ" +
                    "PpQqRrSsTt" +
                    "UuÙùÚúỦủŨũỤụ" +
                    "ƯưỪừỨứỬửỮữỰự" +
                    "VvXx" +
                    "YyỲỳÝýỶỷỸỹỴỵ" +
                    "0123456789"
    );

    private char[] alphabet;
    private int[] inverse;
    private final int R;

    public VNAlphabet(String alpha) {
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < alpha.length(); i++) {
            char c = alpha.charAt(i);
            if (unicode[c])
                throw new IllegalArgumentException("Illegal alphabet: repeated character = '" + c + "'");
            unicode[c] = true;
        }

        alphabet = alpha.toCharArray();
        R = alpha.length();
        inverse = new int[Character.MAX_VALUE];
        for (int i = 0; i < inverse.length; i++)
            inverse[i] = -1;

        for (int c = 0; c < R; c++)
            inverse[alphabet[c]] = c;
    }

    private VNAlphabet(int radix) {
        this.R = radix;
        alphabet = new char[R];
        inverse = new int[R];

        for (int i = 0; i < R; i++)
            alphabet[i] = (char) i;
        for (int i = 0; i < R; i++)
            inverse[i] = i;
    }

    public VNAlphabet() {
        this(256);
    }

    public boolean contains(char c) {
        return c < inverse.length && inverse[c] != -1;
    }

    public int radix() {
        return R;
    }

    public int toIndex(char c) {
        if (c >= inverse.length || inverse[c] == -1) {
            throw new IllegalArgumentException("Ký tự '" + c + "' không có trong bảng chữ cái định nghĩa.");
        }
        return inverse[c];
    }

    public int[] toIndices(String s) {
        char[] source = s.toCharArray();
        int[] target = new int[s.length()];
        for (int i = 0; i < source.length; i++)
            target[i] = toIndex(source[i]);
        return target;
    }

    public char toChar(int index) {
        if (index < 0 || index >= R) {
            throw new IllegalArgumentException("index must be between 0 and " + R + ": " + index);
        }
        return alphabet[index];
    }

    public String toChars(int[] indices) {
        StringBuilder s = new StringBuilder(indices.length);
        for (int i = 0; i < indices.length; i++)
            s.append(toChar(indices[i]));
        return s.toString();
    }

    public static class VietnameseNameComparator implements Comparator<String> {

        @Override
        public int compare(String line1, String line2) {
            String name1 = getNameFromLine(line1);
            String name2 = getNameFromLine(line2);

            int result = compareBasedOnAlphabet(name1, name2);

            if (result == 0) {
                return compareBasedOnAlphabet(line1, line2);
            }
            return result;
        }

        /**
         * Hàm trích xuất Tên từ dòng chứa "Họ Tên MãSV"
         */
        private String getNameFromLine(String line) {
            if (line == null || line.trim().isEmpty()) return "";

            String[] parts = line.trim().split("\\s+");

            if (parts.length < 2) {
                return parts[0];
            }

            return parts[parts.length - 2];
        }

        private int compareBasedOnAlphabet(String s1, String s2) {
            int n1 = s1.length();
            int n2 = s2.length();
            int min = Math.min(n1, n2);

            for (int i = 0; i < min; i++) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 != c2) {
                    int idx1 = VIETNAMESE.toIndex(c1);
                    int idx2 = VIETNAMESE.toIndex(c2);

                    if (idx1 != -1 && idx2 != -1) {
                        return idx1 - idx2;
                    }
                    else {
                        return c1 - c2;
                    }
                }
            }
            return n1 - n2;
        }
    }

    public static void main(String[] args) {
        String filePath = "src/buoi1/VNstudents.txt";
        List<String> studentNames = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Lỗi: Không tìm thấy file tại " + file.getAbsolutePath());
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        studentNames.add(line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("--- Danh sách ban đầu ---");
        for (String name : studentNames) {
            System.out.println(name);
        }

        Collections.sort(studentNames, new VietnameseNameComparator());

        System.out.println("\n--- Danh sách sau khi sắp xếp Tiếng Việt ---");
        for (String name : studentNames) {
            System.out.println(name);
        }
    }
}
