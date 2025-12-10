package buoi9;

import java.util.List;

public class Interval2D {
    private final Interval1D x;
    private final Interval1D y;

    public Interval2D(Interval1D x, Interval1D y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersects(Interval2D that) {
        if (!this.x.intersects(that.x)) return false;
        if (!this.y.intersects(that.y)) return false;
        return true;
    }

    public boolean contains(Interval2D that) {
        return this.x.contains(that.x) && this.y.contains(that.y);
    }

    public boolean contains(Point2D p) {
        return x.contains(p.x())  && y.contains(p.y());
    }

    public double area() {
        return x.length() * y.length();
    }

    public String toString() {
        return x + " x " + y;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Interval2D that = (Interval2D) other;
        return this.x.equals(that.x) && this.y.equals(that.y);
    }

    public int hashCode() {
        int hash1 = x.hashCode();
        int hash2 = y.hashCode();
        return 31*hash1 + hash2;
    }

    public static void main(String[] args) {
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(3, 4);

        Interval1D xInterval = new Interval1D(p1.x(), p2.x());
        Interval1D yInterval = new Interval1D(p1.y(), p2.y());
        Interval2D rect = new Interval2D(xInterval, yInterval);

        List<Point2D> points = List.of(
            new Point2D(2, 2),
            new Point2D(4, 5),
            new Point2D(1, 1),
            new Point2D(3, 4)
        );
        for (Point2D p : points) {
            if (rect.contains(p)) {
                System.out.println("Point " + p + " is inside the rectangle " + rect);
            } else {
                System.out.println("Point " + p + " is outside the rectangle " + rect);
            }
        }
    }
}
