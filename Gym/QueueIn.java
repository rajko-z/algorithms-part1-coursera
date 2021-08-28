/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public interface QueueIn<T> extends Iterable<T> {
    void enqueue(T item);
    T dequeue();
    int size();
    boolean isEmpty();
}
