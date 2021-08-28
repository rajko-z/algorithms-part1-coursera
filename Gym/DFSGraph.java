/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;
import edu.princeton.cs.algs4.Queue;

public class DFSGraph {
    private int source;
    private boolean[] marked;
    private int[] paths;
    private int count;
    private int V;

    // get graph and source vertex
    public DFSGraph(Graph g, int s) {
        this.source = s;
        this.V = g.V();
        this.count = 0;
        this.marked = new boolean[g.V()];
        this.paths = new int[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph g, int curr) {
        marked[curr] = true;
        for (int v : g.adj(curr)) {
            if (!marked[v]) {
                ++count;
                paths[v] = curr;
                dfs(g, v);
            }
        }
    }

    // if every vertex in graph is connected to source vertex;
    public boolean connected() {
        return count == this.V - 1;
    }

    // if vertex v has path to source vertex (eg. is connected to source)
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    // return Iterable that represents the path from source vertex to v vertex
    // or null if there is no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Queue<Integer> iter = new Queue<>();
        int curr = v;
        while (curr != source) {
            iter.enqueue(curr);
            curr = paths[curr];
        }
        iter.enqueue(source);
        return iter;
    }

    // returns the number of vertices connected to source vertex
    public int count() {
        return this.count;
    }


    public static void main(String[] args) {
        Graph graph = GraphGenerator.simple(6, 3);
        System.out.println(graph);
        int source = 3;
        DFSGraph dfs = new DFSGraph(graph, source);
        System.out.println(dfs.connected());
        System.out.println(dfs.count());
        System.out.println(dfs.hasPathTo(1));
        System.out.println(dfs.hasPathTo(2));
        System.out.println(dfs.hasPathTo(4));
        System.out.println(dfs.hasPathTo(5));


        int checkPath = 0;
        if (dfs.hasPathTo(checkPath)) {
            System.out.println("Path from: " + checkPath);
            for (int v : dfs.pathTo(checkPath)) {
                System.out.print(v);
            }
            System.out.println();
            System.out.println("----------------------");
        }

        checkPath = 1;
        if (dfs.hasPathTo(checkPath)) {
            System.out.println("Path from: " + checkPath);
            for (int v : dfs.pathTo(checkPath)) {
                System.out.print(v);
            }
            System.out.println();
            System.out.println("----------------------");
        }

        checkPath = 2;
        if (dfs.hasPathTo(checkPath)) {
            System.out.println("Path from: " + checkPath);
            for (int v : dfs.pathTo(checkPath)) {
                System.out.print(v);
            }
            System.out.println();
            System.out.println("----------------------");
        }

        checkPath = 4;
        if (dfs.hasPathTo(checkPath)) {
            System.out.println("Path from: " + checkPath);
            for (int v : dfs.pathTo(checkPath)) {
                System.out.print(v);
            }
            System.out.println();
            System.out.println("----------------------");
        }

        checkPath = 5;
        if (dfs.hasPathTo(checkPath)) {
            System.out.println("Path from: " + checkPath);
            for (int v : dfs.pathTo(checkPath)) {
                System.out.print(v);
            }
            System.out.println();
            System.out.println("----------------------");
        }

    }
}
