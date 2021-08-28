/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

// from textbook, page 577
public class DigraphCycleDetection {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // vertices on a cycle (if one exists)
    private boolean[] onStack; // vertices on recursive call stack


    public DigraphCycleDetection(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v)
    {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v))
            if (this.hasCycle()) return;
            else if (!marked[w])
            { edgeTo[w] = v; dfs(G, w); }
            else if (onStack[w])
            {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        onStack[v] = false;
    }

    public boolean hasCycle() { return cycle != null; }
    public Iterable<Integer> cycle() { return cycle; }


    public static void main(String[] args) {
        Digraph g1 = new Digraph(7);
        g1.addEdge(0, 5);
        g1.addEdge(0, 2);
        g1.addEdge(0, 1);
        g1.addEdge(3, 6);
        g1.addEdge(3, 5);
        g1.addEdge(3, 4);
        g1.addEdge(5, 2);
        g1.addEdge(6, 4);
        g1.addEdge(6, 0);
        g1.addEdge(3, 2);
        g1.addEdge(1, 4);

        DigraphCycleDetection cd1 = new DigraphCycleDetection(g1);
        if (cd1.hasCycle()) {
            for (int v : cd1.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        } else System.out.println("no cycle");


        Digraph g2 = new Digraph(7);
        g2.addEdge(0, 5);
        g2.addEdge(0, 2);
        g2.addEdge(0, 1);
        g2.addEdge(3, 6);
        g2.addEdge(3, 5);
        g2.addEdge(3, 4);
        g2.addEdge(5, 2);
        g2.addEdge(6, 4);
        g2.addEdge(6, 0);
        g2.addEdge(3, 2);
        g2.addEdge(1, 4);
        g2.addEdge(2, 3);
        DigraphCycleDetection cd2 = new DigraphCycleDetection(g2);

        if (cd2.hasCycle()) {
            for (int v : cd2.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        } else System.out.println("no cycle");



    }
}
