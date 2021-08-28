/* *****************************************************************************
 *  Name: Rajko Zagorac
 *  Date: 21.2.2021
 *  Description: Deque implementation using double linked list
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Node prev;
        private Node next;
        private Item item;
        Node(Item item) { this.item = item; }
    }
    private int size;
    private Node first, last;

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the deque,mnb
    public int size() { return size; }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item);
        newNode.prev = null;
        newNode.next = first;
        if (first != null)
            first.prev = newNode;
        else
            last = newNode;
        first = newNode;
        ++size;
    }
    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item);
        newNode.prev = last;
        newNode.next = null;
        if (last != null)
            last.next = newNode;
        else
            first = newNode;
        last = newNode;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item retVal = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        --size;
        return retVal;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item retVal = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        --size;
        return retVal;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIt(); }

    private class DequeIt implements Iterator<Item> {
        private Node current;
        private int sz;
        public DequeIt() {
            current = first;
            sz = size;
        }
        public boolean hasNext() { return sz > 0; }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item retVal = current.item;
            current = current.next;
            --sz;
            return retVal;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dek = new Deque<>();
        dek.addLast("coas");
        dek.removeLast();
        dek.addLast("coas");
        dek.removeFirst();
        dek.addFirst("not");
        dek.addLast("doing");
        dek.addFirst("am");
        dek.addFirst("I");
        dek.addLast("shit");
        dek.addLast("today!");
        dek.addFirst("I");
        dek.addLast("today");
        dek.removeFirst();
        dek.removeLast();

        for (String item: dek)
            System.out.println(item);

        System.out.println(dek.isEmpty());
        System.out.println(dek.size());

        System.out.println("================");

        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());         //==> true
        System.out.println(deque.isEmpty());        //==> true
        deque.addFirst(3);
        System.out.println(deque.isEmpty());         //==> false
        System.out.println(deque.isEmpty());        //==> false
        System.out.println(deque.removeLast());     // ==> 3
        deque.addFirst(7);
        deque.addFirst(8);
        System.out.println(deque.removeLast()); //==> 7
        System.out.println(deque.removeLast());//
        System.out.println("====");
        for (Integer i: deque)
            System.out.println(i);

        Deque<Double> deque1 = new Deque<>();
        for (Double d: deque1)
            System.out.println(d);
    }
}
