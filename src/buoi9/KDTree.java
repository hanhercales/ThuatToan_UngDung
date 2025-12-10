package buoi9;

import java.util.Arrays;

class KDTree {
    private static final int K = 2;

    static class Node {
        int[] point;
        Node left, right;

        public Node(int[] arr) {
            this.point = Arrays.copyOf(arr, K);
            this.left = this.right = null;
        }
    }

    Node root;

    KDTree() {
        root = null;
    }

    Node insertRec(Node root, int[] point, int depth) {
        if (root == null) return new Node(point);

        int cd = depth % K;

        if (point[cd] < root.point[cd]) root.left = insertRec(root.left, point, depth + 1);
        else root.right = insertRec(root.right, point, depth + 1);

        return root;
    }

    void insert(int[] point) {
        root = insertRec(root, point, 0);
    }

    void rangeSearchRec(Node root, int[] lower, int[] upper, int depth) {
        if (root == null) return;

        boolean inside = true;
        for (int i = 0; i < K; i++)
            if (root.point[i] < lower[i] || root.point[i] > upper[i]) inside = false;

        if (inside) System.out.println(Arrays.toString(root.point));

        int cd = depth % K;

        if (lower[cd] <= root.point[cd]) rangeSearchRec(root.left, lower, upper, depth + 1);

        if (upper[cd] >= root.point[cd]) rangeSearchRec(root.right, lower, upper, depth + 1);
    }

    void rangeSearch(int[] lower, int[] upper) {
        rangeSearchRec(root, lower, upper, 0);
    }

    private int distSq(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        return dx * dx + dy * dy;
    }

    Node nearestRec(Node root, int[] target, int depth, Node best, int bestDist) {
        if (root == null) return best;

        int d = distSq(root.point, target);
        if (d < bestDist) {
            bestDist = d;
            best = root;
        }

        int cd = depth % K;
        Node next = target[cd] < root.point[cd] ? root.left : root.right;
        Node other = target[cd] < root.point[cd] ? root.right : root.left;

        best = nearestRec(next, target, depth + 1, best, bestDist);
        bestDist = distSq(best.point, target);

        int diff = target[cd] - root.point[cd];
        if (diff * diff < bestDist) best = nearestRec(other, target, depth + 1, best, bestDist);

        return best;
    }

    int[] nearest(int[] target) {
        Node best = nearestRec(root, target, 0, root, distSq(root.point, target));
        return best.point;
    }

    public static void main(String[] args) {
        KDTree tree = new KDTree();
        int[][] points = {
                {2, 3},
                {4, 7},
                {5, 4},
                {7, 2},
                {8, 1},
                {6, 5},
                {3, 6}
        };

        for (int[] p : points) {
            tree.insert(p);
        }

        int[] lower = {3, 2};
        int[] upper = {7, 5};

        int[] query = {6, 3};

        System.out.println("\n=== TÌM CÁC ĐIỂM TRONG HÌNH CHỮ NHẬT ===");
        System.out.println("Lower = " + Arrays.toString(lower));
        System.out.println("Upper = " + Arrays.toString(upper));
        System.out.println("Kết quả:");

        tree.rangeSearch(lower, upper);

        System.out.println("\n=== TÌM ĐIỂM GẦN NHẤT ===");
        System.out.println("Điểm truy vấn: " + Arrays.toString(query));

        int[] nearest = tree.nearest(query);

        System.out.println("Điểm gần nhất: " + Arrays.toString(nearest));
    }
}