package buoi9._33_SegmentIntersection;

import buoi9._30_BST.BST;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SegmentIntersection {

    public static class Point implements Comparable<Point> {
        double x, y;
        double id;

        public Point(double id, double x, double y) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

        @Override
        public int compareTo(Point other) {
            return Double.compare(id, other.id);
        }

        @Override
        public String toString() {
            return "(" + id + ": " + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point other = (Point) obj;
            return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0 && id == other.id;
        }
    }

    public static class ComparatorByX implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p1.x, p2.x);
        }
    }

    public static boolean isVertical(Point p1, Point p2) {
        return p1.x == p2.x;
    }

    public static void main(String[] args) {
        List<Point> points = Stream.of(new Point(1, 1.0, 1.0),
                new Point(1, 4.0, 1.0),
                new Point(2, 5.0, 5.0),
                new Point(2, 5.0, 8.0),
                new Point(3, 1.0, 8.0),
                new Point(3, 6.0, 8.0),
                new Point(4, 2.0, 5.0),
                new Point(4, 7.0, 5.0),
                new Point(5, 3.0, 3.0),
                new Point(5, 6.0, 3.0)).sorted(new ComparatorByX()).toList();

        BST<Double, Integer> bst = new BST<>();
        BST<Point, Point> pointBST = new BST<>();
        for (Point p : points) {
            Point oldPoint = pointBST.get(p);
            if (oldPoint == null) {
                pointBST.put(p, p);
                if (bst.get(p.y) != null) {
                    bst.put(p.y, bst.get(p.y) + 1);
                } else {
                    bst.put(p.y, 1);
                }
            } else if (isVertical(p, oldPoint)) {
                pointBST.delete(oldPoint);
                bst.put(oldPoint.y, bst.get(oldPoint.y) - 1);
                if (bst.get(oldPoint.y) == 0) {
                    bst.delete(oldPoint.y);
                }
                bst.search(Math.min(p.y, oldPoint.y),
                        Math.max(p.y,
                                oldPoint.y)).forEach((y) -> System.out.println("(" + p.x + ", " + y + ") is an intersection point."));
            } else {
                pointBST.delete(oldPoint);
                bst.put(p.y, bst.get(p.y) - 1);
                if (bst.get(p.y) == 0) {
                    bst.delete(p.y);
                }
            }
        }
    }
}
