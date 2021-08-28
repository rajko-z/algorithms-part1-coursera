/* *****************************************************************************
 *  Name: Rajko Zagorac
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] segmentS;

    public FastCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        Point[] pointsCopy = copyPoints(points);
        calculateSegments(pointsCopy);
    }

    private Point[] copyPoints(Point[] points) {
        Point[] pointsCopy = new Point[points.length];
        for (int i = 0; i < points.length; ++i) pointsCopy[i] = points[i];
        return pointsCopy;
    }

    public int numberOfSegments() { return segmentS.length; }
    public LineSegment[] segments() { return segmentS; }

    private void addSegment(Point[] points, ResizingArrayQueue<LineSegment> copySegments,
                            int begIndex, int endIndex)
    {
        Point maxx = points[0];
        Point minn = points[0];
        for (int i = begIndex; i <= endIndex; ++i) {
            if (points[i].compareTo(maxx) > 0) maxx = points[i];
            if (points[i].compareTo(minn) < 0) minn = points[i];
        }
        if (minn.compareTo(points[0]) == 0)
            copySegments.enqueue(new LineSegment(minn, maxx));
    }

    private void addSegments(Point[] points, ResizingArrayQueue<LineSegment> copySegments)
    {
        int i = 1;
        while (i < points.length) {
            double currSlop = points[0].slopeTo(points[i]);
            int counter = 1;

            int begIndex = i;
            while ((i+1) < points.length && points[0].slopeTo(points[i+1]) == currSlop) {
                ++i;
                ++counter;
            }
            int endIndex = i;

            if (counter >= 3) {
                addSegment(points, copySegments, begIndex, endIndex);
            }
            ++i;
        }

    }

    private void calculateSegments(Point[] points) {
        ResizingArrayQueue<LineSegment> segmentsCopy = new ResizingArrayQueue<>();
        Point[] pointsCopy = copyPoints(points);
        for (int i = 0; i < pointsCopy.length; ++i) {
            Arrays.sort(points, pointsCopy[i].slopeOrder());
            addSegments(points, segmentsCopy);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
