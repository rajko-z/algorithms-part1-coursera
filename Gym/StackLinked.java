/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackLinked<T> implements StackIn<T> {
    private class Node {
        private T item;
        private Node next;
        Node(T item) { this.item = item; }
    }
    private int size = 0;
    private Node first;

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    @Override
    public void push(T item) {
        Node newNode = new Node(item);
        newNode.next = first;
        first = newNode;
        ++size;
    };

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException("Stack is empty!");
        Node currFirst = first;
        first = first.next;
        --size;
        return currFirst.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackLinkedIt();
    }

    private class StackLinkedIt implements Iterator<T> {
        private Node curr = first;
        public boolean hasNext() { return curr != null; }
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T retVal = curr.item;
            curr = curr.next;
            return retVal;
        }
    }


    public static void main(String[] args) {
        StackLinked<String> stackStr = new StackLinked<>();
        stackStr.push("Don't");
        stackStr.push("Don't");
        stackStr.pop();
        stackStr.push("do");
        stackStr.push("shit");
        stackStr.push("today");
        stackStr.push("today");
        stackStr.pop();

        for (String val : stackStr) {
            System.out.println(val + " ");
        }
    }
}
