/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueLinked<T> implements QueueIn<T> {
    private int size;
    private class Node {
        T item;
        Node next;
        Node(T item) { this.item = item; }
    }
    private Node first, last;

    public QueueLinked() { }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    @Override
    public void enqueue(T item) {
        Node newNode = new Node(item);
        if (last == null)
            first = newNode;
        else
            last.next = newNode;
        ++size;
        last = newNode;
    }

    @Override
    public T dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty!");
        --size;
        Node retVal = first;
        if (first.next == null) { first = null; last = null; }
        else first = first.next;
        return retVal.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueLinkedIt();
    }

    private class QueueLinkedIt implements Iterator<T> {
        private Node curr = first;
        private int i = size;
        public boolean hasNext() { return i > 0; }
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T retVal = curr.item;
            --i;
            curr = curr.next;
            return retVal;
        }
    }

    public static void main(String[] args) {
        QueueLinked<String> queue = new QueueLinked<>();

        queue.enqueue("I'm");
        queue.enqueue("I'm");
        queue.enqueue("not");
        queue.enqueue("doing");
        queue.enqueue("shit");
        queue.enqueue("today");
        queue.dequeue();

        for (String item: queue)
            System.out.println(item);

    }
}
