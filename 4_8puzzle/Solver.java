/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

// =================================================
    private class Node implements Comparable<Node> {
        private Node prev;
        private Board bd;
        private int manhattan;
        private int cntMoves;

        private Node(Node prev, Board bd, int cntMoves) {
            this.prev = prev;
            this.bd = bd;
            this.cntMoves = cntMoves;
            this.manhattan = this.bd.manhattan();
        }

        public int compareTo(Node other) {
            int currNodeValue = this.manhattan + this.cntMoves;
            int otherNodeValue = other.manhattan + other.cntMoves;
            if (currNodeValue < otherNodeValue) return -1;
            if (currNodeValue > otherNodeValue) return 1;
            return Integer.compare(this.manhattan, other.manhattan);
        }
    }
//===================================
    private Node endNode;
    private boolean solvable;
// =======================================


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Board twin = initial.twin();
        MinPQ<Node> pqOrigin = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();

        Node endNodeOrigin = new Node(null, initial, 0);
        Node endNodeTwin = new Node(null, twin, 0);

        while (!endNodeOrigin.bd.isGoal() && !endNodeTwin.bd.isGoal()) {
            insertToPQValidNeighbors(pqOrigin, endNodeOrigin);
            endNodeOrigin = pqOrigin.delMin();

            insertToPQValidNeighbors(pqTwin, endNodeTwin);
            endNodeTwin = pqTwin.delMin();
        }
        if (endNodeOrigin.bd.isGoal()) {
            endNode = endNodeOrigin;
            this.solvable = true;
        } else {
            this.solvable = false;
        }
    }

    private void insertToPQValidNeighbors(MinPQ<Node> pq, Node parent) {
        for (Board neighbor : parent.bd.neighbors()) {
            if (parent.prev == null || !neighbor.equals(parent.prev.bd))
                pq.insert(new Node(parent, neighbor, parent.cntMoves + 1));
        }
    }

    public boolean isSolvable() { return this.solvable; }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return endNode.cntMoves;
        else              return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        ArrayList<Board> items = new ArrayList<>();
        items.add(endNode.bd);
        Node currNode = endNode;
        while (currNode.prev != null) {
            items.add(currNode.prev.bd);
            currNode = currNode.prev;
        }
        Collections.reverse(items);
        return items;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
