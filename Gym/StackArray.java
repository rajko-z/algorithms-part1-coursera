/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackArray<T> implements StackIn<T> {

    private T[] data;
    private int size = 0;
    public StackArray() { data = (T[]) new Object[2]; }

    @Override
    public void push(T item) {
        if (size == data.length) resize(size * 2);
        data[size++] = item;
    }

    private void resize(int capacity) {
        T[] dataCopy = (T[]) new Object[capacity];
        for (int i = 0; i < size; ++i)
            dataCopy[i] = data[i];
        data = dataCopy;
    }

    @Override
    public T pop() {
        if (size == 0) throw new NoSuchElementException("Stack is empty!");
        T retVal = data[--size];
        data[size] = null; // prevent loitering
        if (size > 0 && size == data.length / 4) resize(data.length / 2);
        return retVal;
    }

    @Override
    public Iterator<T> iterator() { return new StackArrayIt(); }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    private class StackArrayIt implements Iterator<T> {
        private int i = size;
        public boolean hasNext() { return i > 0; }
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[--i];
        }
    }

    public static void main(String[] args) {
        StackArray<String> stackStr = new StackArray<>();
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
