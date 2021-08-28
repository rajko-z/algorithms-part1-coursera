/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> red = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            red.enqueue(value);
        }
        for (int i = 0; i < k; ++i) {
            System.out.println(red.sample());
        }
    }
}
