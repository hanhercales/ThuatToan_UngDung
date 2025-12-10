package buoi9;

import java.util.Comparator;

public class Interval1D implements Comparable<Interval1D> {
    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = new MinEndpointComparator();

    /**
     * Compares two intervals by max endpoint.
     */
    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = new MaxEndpointComparator();

    /**
     * Compares two intervals by length.
     */
    public static final Comparator<Interval1D> LENGTH_ORDER = new LengthComparator();

    public final double min;
    public final double max;

    public Interval1D(double min, double max) {
        if (Double.isInfinite(min) || Double.isInfinite(max))
            throw new IllegalArgumentException("Endpoints must be finite");
        if (Double.isNaN(min) || Double.isNaN(max)) throw new IllegalArgumentException("Endpoints cannot be NaN");

        // convert -0.0 to +0.0
        if (min == 0.0) min = 0.0;
        if (max == 0.0) max = 0.0;

        if (min <= max) {
            this.min = min;
            this.max = max;
        } else throw new IllegalArgumentException("Illegal interval");
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public boolean intersects(Interval1D that) {
        if (this.max < that.min) return false;
        if (that.max < this.min) return false;
        return true;
    }

    public boolean contains(Interval1D that) {
        return (this.max >= that.max) && (this.min <= that.min);
    }

    public boolean contains(double x) {
        return (min <= x) && (x <= max);
    }

    public double length() {
        return max - min;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Interval1D that = (Interval1D) other;
        return this.min == that.min && this.max == that.max;
    }

    public int hashCode() {
        int hash1 = ((Double) min).hashCode();
        int hash2 = ((Double) max).hashCode();
        return 31 * hash1 + hash2;
    }

    @Override
    public int compareTo(Interval1D o) {
        if (this.min < o.min) return -1;
        else if (this.min > o.min) return +1;
        else return Double.compare(this.max, o.max);
    }

    // ascending order of min endpoint, breaking ties by max endpoint
    private static class MinEndpointComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            if (a.min < b.min) return -1;
            else if (a.min > b.min) return +1;
            else if (a.max < b.max) return -1;
            else if (a.max > b.max) return +1;
            else return 0;
        }
    }

    // ascending order of max endpoint, breaking ties by min endpoint
    private static class MaxEndpointComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            if (a.max < b.max) return -1;
            else if (a.max > b.max) return +1;
            else if (a.min < b.min) return -1;
            else if (a.min > b.min) return +1;
            else return 0;
        }
    }

    // ascending order of length
    private static class LengthComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            double alen = a.length();
            double blen = b.length();
            if (alen < blen) return -1;
            else if (alen > blen) return +1;
            else return 0;
        }
    }

}
