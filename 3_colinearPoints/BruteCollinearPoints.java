/* *****************************************************************************
 *  Name: Rajko Zagorac
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segmentS;

    public BruteCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        Point[] pointsCopy = copyPoints(points);
        calculateSegments(pointsCopy);
    }

    public int numberOfSegments() { return segmentS.length; }
    public LineSegment[] segments() { return segmentS; }

    private Point[] copyPoints(Point[] points) {
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; ++i) pointsCopy[i] = points[i];
        return pointsCopy;
    }

    private boolean is4PointSegment(Point a, Point b, Point c, Point d) {
        double slope = a.slopeTo(b);
        if (slope == a.slopeTo(c) && slope == a.slopeTo(d)) return true;
        return false;
    }

    private LineSegment getSegmentForPoints(Point[] points) {
        Point maxx = points[0];
        Point minn = points[0];
        for (int i = 1; i < points.length; ++i) {
            if (points[i].compareTo(maxx) > 0) maxx = points[i];
            if (points[i].compareTo(minn) < 0) minn = points[i];
        }
        return new LineSegment(minn, maxx);
    }


    private void calculateSegments(Point[] points) {
        ResizingArrayQueue<LineSegment> segmentsCopy = new ResizingArrayQueue<>();

        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                for (int k = j + 1; k < points.length; ++k) {
                    for (int p = k + 1; p < points.length; ++p) {
                        if (is4PointSegment(points[i], points[j], points[k], points[p])) {
                            Point[] temp = new Point[]{points[i], points[j], points[k], points[p]};
                            segmentsCopy.enqueue(getSegmentForPoints(temp));
                        }
                    }
                }
            }
        }

        segmentS = new LineSegment[segmentsCopy.size()];
        int index = 0;
        while (!segmentsCopy.isEmpty()) segmentS[index++] = segmentsCopy.dequeue();
    }


    private boolean validate(Point[] points) {
        if (points == null) return false;
        for (Point p: points)
            if (p == null) return false;

        // check for duplicates
        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].compareTo(points[j]) == 0) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
