/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MyUnionTest {

    private int[] id;
    private int[] sz;

    public MyUnionTest(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; ++i) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    private int getRoot(int i) {
        while (id[i] != i) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    // union method for quickFind
    // public void union(int p, int q) {
    //     for (int i = 0; i < id.length; ++i) {
    //         if (id[i] == id[p]) id[i] = id[q];
    //     }
    // }

    // union method for regular quickUnion without weights
    // public void union(int p, int q) {
    //     int pRoot = getRoot(p);
    //     int qRoot = getRoot(q);
    //     id[pRoot] = qRoot;
    // }

    public void union(int p, int q) {
        int pRoot = getRoot(p);
        int qRoot = getRoot(q);
        if (pRoot == qRoot) return;
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }

    }

    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        MyUnionTest uf = new MyUnionTest(N);
        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }


}
