/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() { return set.isEmpty(); }

    // number of points in the set
    public int size() { return set.size(); }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() { for (Point2D p : set) p.draw(); }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : set)
            if (rect.contains(p)) queue.enqueue(p);
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        Point2D bestPt = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for (Point2D pt : set) {
            double currDist = pt.distanceSquaredTo(p);
            if (currDist < bestDist) {
                bestPt = pt;
                bestDist = currDist;
            }
        }
        return bestPt;
    }

    public static void main(String[] args) {

    }
}
