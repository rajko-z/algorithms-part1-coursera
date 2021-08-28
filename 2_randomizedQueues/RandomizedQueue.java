/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }
    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // resize the underlying array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int br = 0;
        int i = 0;
        while (br < n) {
            int index = (first + i) % q.length;
            if (q[index] != null) {
                copy[br] = q[index];
                ++br;
            }
            ++i;
        }
        q = copy;
        first = 0;
        last  = n;
    }

    // add the item
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length || q[(last + 1) % q.length] != null) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }

    private int getRandomIndex() {
        int retVal;
        do {
            if (first <= last)
                retVal = StdRandom.uniform(first, last + 1);
            else
                retVal = StdRandom.uniform(0, q.length);
        } while (q[retVal] == null);
        return retVal;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randomIndex = getRandomIndex();
        Item item = q[randomIndex];
        q[randomIndex] = null;
        --n;
        if (randomIndex == first) {
            ++first;
            if (first == q.length) first = 0;
        }
        else if (randomIndex == last) {
            --last;
            if (last < 0) last = q.length - 1;
        }
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randomIndex = getRandomIndex();
        return q[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedQueueIt(); }

    private class RandomizedQueueIt implements Iterator<Item> {
        private int size;
        private int index;
        private Item[] arr;

        public RandomizedQueueIt() {
            size = n;
            index = 0;
            arr = (Item[]) new Object[q.length];
            for (int i = 0; i < q.length; ++i)
                arr[i] = q[i];
            StdRandom.shuffle(arr);
        }
        public boolean hasNext() { return size > 0; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            while (arr[index] == null) ++index;
            Item retVal = arr[index];
            ++index;
            --size;
            return retVal;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<String> rqueue = new RandomizedQueue<>();
        rqueue.enqueue("today");
        rqueue.enqueue("shit");
        rqueue.enqueue("doing");
        rqueue.enqueue("not");
        rqueue.enqueue("am");
        rqueue.dequeue();
        rqueue.dequeue();
        String getSample = rqueue.sample();
        System.out.println(getSample);
        System.out.println(rqueue.size());
        System.out.println(rqueue.isEmpty());
        rqueue.enqueue("I");
        System.out.println("=====ITER====");
        for (String item: rqueue)
            System.out.println(item);
        System.out.println("===");

        for (String item: rqueue)
            System.out.println(item);

    }
}

