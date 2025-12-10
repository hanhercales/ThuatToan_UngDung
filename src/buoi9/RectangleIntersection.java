package buoi9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RectangleIntersection {
    public static class Rectangle implements Comparable<Rectangle> {
        double x1, y1, x2, y2;
        int id;

        public Rectangle(int id, double x1, double y1, double x2, double y2) {
            this.id = id;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public int compareTo(Rectangle other) {
            return Integer.compare(id, other.id);
        }

        @Override
        public String toString() {
            return "Rectangle " + id + ": [(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")]";
        }
    }

    public static class XEvent implements Comparable<XEvent> {
        double x;
        Rectangle rectangle;
        boolean isStart;

        public XEvent(double x, Rectangle rectangle, boolean isStart) {
            this.x = x;
            this.rectangle = rectangle;
            this.isStart = isStart;
        }

        @Override
        public int compareTo(XEvent o) {
            return Double.compare(this.x, o.x);
        }
    }

    public static void main(String[] args) {
        List<Rectangle> rectangles = Stream.of(
                new Rectangle(1, 1.0, 1.0, 4.0, 4.0),
                new Rectangle(2, 3.0, 3.0, 6.0, 6.0),
                new Rectangle(3, 5.0, 5.0, 8.0, 8.0),
                new Rectangle(4, 7.0, 7.0, 10.0, 10.0),
                new Rectangle(5, 2.0, 2.0, 5.0, 5.0)
        ).toList();

        List<XEvent> events = new ArrayList<>();
        for (Rectangle rect : rectangles) {
            events.add(new XEvent(rect.x1, rect, true));
            events.add(new XEvent(rect.x2, rect, false));
        }
        events.sort(null);

        IntervalTree<XEvent> intervalTree = new IntervalTree<>();
        for (XEvent event : events) {
            Interval1D yInterval = new Interval1D(event.rectangle.y1, event.rectangle.y2);
            if (event.isStart) {
                intervalTree.put(yInterval, event);
            } else {
                intervalTree.remove(yInterval);
                Iterable<Interval1D> overlappingIntervals = intervalTree.searchAll(yInterval);
                for (Interval1D interval : overlappingIntervals) {
                    XEvent overlappingEvent = intervalTree.get(interval);
                    System.out.println("Intersection between " + event.rectangle + " and " + overlappingEvent.rectangle);
                }
            }
        }
    }
}
