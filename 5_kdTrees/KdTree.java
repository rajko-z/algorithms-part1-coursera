/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {

    private static final boolean RED = true;
    private static final boolean BLUE = false;

    private Node root;
    private int size;

    // save current best while going through recursion
    private Point2D bestPoint;
    private double bestDist;

    private static class Node implements Comparable<Point2D> {
        private Node left, right;
        private Point2D pt;
        private boolean color;
        private RectHV rc;

        public Node(Point2D pt, RectHV rc, boolean color) {
            this.pt = pt;
            this.rc = rc;
            this.color = color;
        }

        public int compareTo(Point2D other) {
            if (color == RED) return Point2D.X_ORDER.compare(pt, other);
            else              return Point2D.Y_ORDER.compare(pt, other);
        }
    }

    public KdTree() {
        size = 0;
        root = null;
        bestPoint = null;
        bestDist = Double.POSITIVE_INFINITY;
    }

    // is the set empty?
    public boolean isEmpty() { return size == 0; }

    // number of points in the set
    public int size() { return size; }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)     throw new IllegalArgumentException();
        if (contains(p))   return;
        ++size;
        if (root == null)  root = new Node(p, new RectHV(0, 0, 1, 1),  RED);
        else               root = insert(root, null, p);
    }

    private Node insert(Node curr, Node pre, Point2D pt) {
        if (curr == null) {
            if (pre.color == RED && pt.x() < pre.pt.x())
                return new Node(pt, new RectHV(pre.rc.xmin(), pre.rc.ymin(), pre.pt.x(), pre.rc.ymax()), !pre.color);
            if (pre.color == RED && pt.x() >= pre.pt.x())
                return new Node(pt, new RectHV(pre.pt.x(), pre.rc.ymin(), pre.rc.xmax(), pre.rc.ymax()), !pre.color);
            if (pre.color == BLUE && pt.y() < pre.pt.y())
                return new Node(pt, new RectHV(pre.rc.xmin(), pre.rc.ymin(), pre.rc.xmax(), pre.pt.y()), !pre.color);
            else
                return new Node(pt, new RectHV(pre.rc.xmin(), pre.pt.y(), pre.rc.xmax(), pre.rc.ymax()), !pre.color);
        }
        if (curr.compareTo(pt) > 0) curr.left = insert(curr.left, curr, pt);
        else                        curr.right = insert(curr.right, curr, pt);
        return                      curr;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node curr, Point2D p) {
        if (curr == null)                return false;
        if (curr.pt.compareTo(p) == 0)   return true;
        if (curr.compareTo(p) > 0)       return contains(curr.left, p);
        else                             return contains(curr.right, p);
    }

    // draw all points to standard draw
    public void draw() {
        drawSecondWay(root);
    }

    private void writeNodeWithLine(Node curr, double x1, double y1, double x2, double y2, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(0.0025);
        StdDraw.line(x1, y1, x2, y2);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        StdDraw.point(curr.pt.x(), curr.pt.y());
    }

    // ugly method but gets job done
    // this is how code looks like when you dont think before writing
    private void drawFirstWay(Node curr, Node f, Node gf, Node ggf) {
        if (curr == null)  return;
        if (f == null) writeNodeWithLine(curr, curr.pt.x(), 0, curr.pt.x(), 1, StdDraw.RED);
        else if (ggf == null) {
            if (curr.color == BLUE) {
                double temp = 0;
                if (curr.pt.x() > f.pt.x()) temp = 1;
                writeNodeWithLine(curr, f.pt.x(), curr.pt.y(), temp, curr.pt.y(), StdDraw.BLUE);
            } else {
                double temp = 0;
                if (curr.pt.y() > f.pt.y()) temp = 1;
                writeNodeWithLine(curr, curr.pt.x(), f.pt.y(), curr.pt.x(), temp, StdDraw.RED);
            }
        } else {
            if (curr.color == BLUE) {
                if (curr.pt.x() > f.pt.x() && curr.pt.x() > ggf.pt.x())
                    writeNodeWithLine(curr, f.pt.x(), curr.pt.y(), 1, curr.pt.y(), StdDraw.BLUE);
                else if (curr.pt.x() < f.pt.x() && curr.pt.x() < ggf.pt.x())
                    writeNodeWithLine(curr, f.pt.x(), curr.pt.y(), 0, curr.pt.y(), StdDraw.BLUE);
                else
                    writeNodeWithLine(curr, f.pt.x(), curr.pt.y(), ggf.pt.x(), curr.pt.y(), StdDraw.BLUE);
            } else {
                if (curr.pt.y() > f.pt.y() && curr.pt.y() > ggf.pt.y())
                    writeNodeWithLine(curr, curr.pt.x(), f.pt.y(), curr.pt.x(), 1, StdDraw.RED);
                else if (curr.pt.y() < f.pt.y() && curr.pt.y() < ggf.pt.y())
                    writeNodeWithLine(curr, curr.pt.x(), f.pt.y(), curr.pt.x(), 0, StdDraw.RED);
                else
                    writeNodeWithLine(curr, curr.pt.x(), f.pt.y(), curr.pt.x(), ggf.pt.y(), StdDraw.RED);
            }
        }
        drawFirstWay(curr.left, curr, f, gf);
        drawFirstWay(curr.right, curr, f, gf);
    }

    private void drawSecondWay(Node curr) {
        if (curr == null) return;
        if (curr.color == RED)
            writeNodeWithLine(curr, curr.pt.x(), curr.rc.ymin(), curr.pt.x(), curr.rc.ymax(), StdDraw.RED);
        else
            writeNodeWithLine(curr, curr.rc.xmin(), curr.pt.y(), curr.rc.xmax(), curr.pt.y(), StdDraw.BLUE);
        drawSecondWay(curr.left);
        drawSecondWay(curr.right);
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> iter = new Queue<>();
        range(root, rect, iter);
        return iter;
    }

    private void range(Node curr, RectHV rect, Queue<Point2D> queue) {
        if (curr == null) return;
        if (rect.contains(curr.pt)) queue.enqueue(curr.pt);
        if (curr.left  != null && rect.intersects(curr.left.rc))  range(curr.left, rect, queue);
        if (curr.right != null && rect.intersects(curr.right.rc)) range(curr.right, rect, queue);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        this.bestPoint = null;
        this.bestDist = Double.POSITIVE_INFINITY;
        nearest(root, p);
        return this.bestPoint;
    }

    private void nearest(Node curr, Point2D p) {
        if (curr == null) return;

        double currVal = curr.pt.distanceSquaredTo(p);
        if (currVal < this.bestDist) {
            this.bestDist = currVal;
            this.bestPoint = curr.pt;
        }
        // left
        if (curr.color == RED && p.x() < curr.pt.x()) {
            nearest(curr.left, p);
            if (!leftRightPruning(curr, p))
                nearest(curr.right, p);
        }
        // right
        else if (curr.color == RED && p.x() >= curr.pt.x()) {
            nearest(curr.right, p);
            if (!leftRightPruning(curr, p))
                nearest(curr.left, p);
        }
        // low
        else if (curr.color == BLUE && p.y() < curr.pt.y()) {
            nearest(curr.left, p);
            if (!lowHighPruning(curr, p))
                nearest(curr.right, p);
        }
        // high
        else if (curr.color == BLUE && p.y() >= curr.pt.y()) {
            nearest(curr.right, p);
            if (!lowHighPruning(curr, p))
                nearest(curr.left, p);
        }

    }

    private boolean leftRightPruning(Node curr, Point2D p) {
        double temp;
        if (p.y() < curr.rc.ymin()) {
            temp = p.distanceSquaredTo(new Point2D(curr.pt.x(), curr.rc.ymin()));
        } else if (p.y() > curr.rc.ymax()) {
            temp = p.distanceSquaredTo(new Point2D(curr.pt.x(), curr.rc.ymax()));
        } else {
            temp = p.distanceSquaredTo(new Point2D(curr.pt.x(), p.y()));
        }
        if (temp >= bestDist) return true;
        return false;
    }

    private boolean lowHighPruning(Node curr, Point2D p) {
        double temp;
        if (p.x() < curr.rc.xmin()) {
            temp = p.distanceSquaredTo(new Point2D(curr.rc.xmin(), curr.pt.y()));
        } else if (p.x() > curr.rc.xmax()) {
            temp = p.distanceSquaredTo(new Point2D(curr.rc.xmax(), curr.pt.y()));
        } else {
            temp = p.distanceSquaredTo(new Point2D(p.x(), curr.pt.y()));
        }
        if (temp >= bestDist) return true;
        return false;
    }


    private Point2D nearestBruteForce(Node curr, Point2D p) {
        if (curr == null) return null;

        Point2D leftBest = nearestBruteForce(curr.left, p);
        Point2D rightBest = nearestBruteForce(curr.right, p);

        double currVal = curr.pt.distanceSquaredTo(p);
        double leftMin = Double.POSITIVE_INFINITY;
        if (leftBest != null) leftMin = leftBest.distanceSquaredTo(p);

        double rightMin = Double.POSITIVE_INFINITY;
        if (rightBest != null) rightMin = rightBest.distanceSquaredTo(p);

        if (currVal <= leftMin && currVal <= rightMin) return curr.pt;
        if (leftMin <= currVal && leftMin <= rightMin) return leftBest;
        else                                           return rightBest;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
    }
}
