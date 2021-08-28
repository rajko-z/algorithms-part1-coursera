/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class TopologicalSort {

    private boolean[] marked;
    private Stack<Integer> stack;

    public TopologicalSort(Digraph g) {
        DigraphCycleDetection dc = new DigraphCycleDetection(g);
        if (dc.hasCycle())
            throw new IllegalArgumentException("Graph has a cycle!");
        marked = new boolean[g.V()];
        stack = new Stack<>();
        for (int i = 0; i < g.V(); ++i) {
            if (!marked[i]) {
                dfs(g, i);
            }
        }
    }

    private void dfs(Digraph g, int curr) {
        for (int v : g.adj(curr)) {
            if (!marked[v])
                dfs(g, v);
        }
        marked[curr] = true;
        stack.push(curr);
    }

    public Iterable<Integer> ordered() {
        return stack;
    }

    public static void main(String[] args) {
        Digraph graph = new Digraph(7);
        graph.addEdge(0, 5);
        graph.addEdge(0, 2);
        graph.addEdge(0, 1);
        graph.addEdge(3, 6);
        graph.addEdge(3, 5);
        graph.addEdge(3, 4);
        graph.addEdge(5, 2);
        graph.addEdge(6, 4);
        graph.addEdge(6, 0);
        graph.addEdge(3, 2);
        graph.addEdge(1, 4);

        System.out.println("--CURRENT GRAPH--");
        System.out.println(graph);
        System.out.println();
        System.out.println("TOPOLOGICAL SORT");
        TopologicalSort tp = new TopologicalSort(graph);
        StringBuilder sb = new StringBuilder();
        String sep = " -> ";
        for (int v : tp.ordered()) {
            sb.append(v + sep);
        }
        sb.delete(sb.length() - sep.length(), sb.length());
        System.out.println(sb.toString());
    }
}
