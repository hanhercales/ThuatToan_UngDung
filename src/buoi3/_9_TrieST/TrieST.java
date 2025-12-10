package buoi3._9_TrieST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TrieST<T> {

    private Node root;
    private int n; // Số lượng từ khóa

    private static class Node {
        private Object val;
        private Map<Character, Node> next = new HashMap<>();
    }

    public TrieST() {
    }

    public T get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (T) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        // Lấy node con từ HashMap
        return get(x.next.get(c), key, d + 1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void put(String key, T value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (value == null) delete(key);
        else root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, T val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);

        x.next.putIfAbsent(c, new Node());

        Node nextNode = x.next.get(c);
        x.next.put(c, put(nextNode, key, val, d + 1));
        return x;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            Node nextNode = x.next.get(c);
            Node resultNode = delete(nextNode, key, d + 1);

            if (resultNode == null) {
                x.next.remove(c);
            } else {
                x.next.put(c, resultNode);
            }
        }

        if (x.val != null) return x;
        if (x.next.isEmpty()) return null;
        return x;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        List<String> results = new ArrayList<>(); // Dùng List để có thể sort nếu cần
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);

        Collections.sort(results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, List<String> results) {
        if (x == null) return;
        if (x.val != null) results.add(prefix.toString());

        // Duyệt qua HashMap
        for (Map.Entry<Character, Node> entry : x.next.entrySet()) {
            prefix.append(entry.getKey());
            collect(entry.getValue(), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        TrieST<Integer> trieST = new TrieST<>();

        try {
            File file = new File("src/buoi3/doc.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Tách từ bằng bất kỳ ký tự nào không phải chữ cái hoặc số
                String[] text = line.split("[^\\p{L}\\p{N}]+");
                for (String word : text) {
                    if (!word.trim().isEmpty()) {
                        trieST.put(word.trim().toLowerCase(), 1); // Lưu chữ thường để tìm kiếm không phân biệt hoa thường
                    }
                }
            }
            scanner.close();
            System.out.println("Đã nạp xong dữ liệu (" + trieST.size() + " từ).");
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file doc.txt. Đang chạy chế độ nhập tay.");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("--- AUTO COMPLETE (Nhập 'exit' để thoát) ---");

        while (true) {
            System.out.print("Nhập từ gợi ý: ");
            String prefix = sc.nextLine().trim().toLowerCase();

            if (prefix.equals("exit")) break;
            if (prefix.isEmpty()) continue;

            Iterable<String> suggestions = trieST.keysWithPrefix(prefix);
            Iterator<String> iterator = suggestions.iterator();

            if (!iterator.hasNext()) {
                System.out.println("-> Không tìm thấy từ nào bắt đầu bằng '" + prefix + "'.");
                trieST.put(prefix, 1);
            } else {
                System.out.println("-> Các từ gợi ý:");
                int count = 0;
                for (String s : suggestions) {
                    System.out.println("   - " + s);
                    count++;
                    if (count >= 10) { // Giới hạn hiển thị 10 gợi ý
                        System.out.println("   ... (và còn nữa)");
                        break;
                    }
                }
            }
            System.out.println();
        }
        sc.close();
    }
}