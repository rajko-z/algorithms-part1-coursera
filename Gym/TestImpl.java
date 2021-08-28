/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class TestImpl {
    public static void main(String[] args) {
        StackIn<String> stackArr = new StackArray<String>();
        stackArr.push("hello");
        stackArr.push("world");
        StackIn<String> stackLinked = new StackLinked<String>();
        stackLinked.push("world");
        stackLinked.push("hello");
        stackLinked.push("hello");
        stackLinked.pop();

        for (String item: stackArr)
            System.out.println(item);
        for (String item: stackLinked)
            System.out.println(item);
    }
}
