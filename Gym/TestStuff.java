/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.RedBlackBST;

public class TestStuff {
    public static void main(String[] args) {

        RedBlackBST<Integer, String> rbs = new RedBlackBST<>();
        rbs.put(34, "a");
        rbs.put(22, "b");
        rbs.put(11, "c");
        rbs.put(15, "d");
        rbs.put(16, "e");
        rbs.put(18, "f");
        rbs.put(5, "g");
        rbs.put(40, "h");
        rbs.put(19, "i");
        rbs.put(4, "j");
        rbs.put(56, "k");

        for (int k : rbs.keys())
            System.out.println(k);









    }
}
