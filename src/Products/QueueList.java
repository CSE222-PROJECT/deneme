package Products;
import java.util.*;

/** Implements the Queue interface using a LinkedList component.
 *   @author Koffman & Wolfgang
 * */

public class QueueList< E >
        implements Queue < E >{

    // Data Field
    private LinkedList < E > theQueue
            = new LinkedList < E > (); // LinkedList that is the queue.

    /** Inserts an item at the rear of the queue.
     @param item The element to add
     @return true (always successful)
     */
    public boolean offer(E item) {
        theQueue.addLast(item);
        return true;
    }

    public E get(int index){
        return theQueue.get(index);
    }

    /** Removes the entry at the front of the queue and returns it.
     @return The item removed if successful, or null if not
     */
    public E poll() {
        if (size() == 0)
            return null;
        else
            return theQueue.remove(0);
    }

    /** Returns the item at the front of the queue without removing it.
     @return The item at the front if successful; null if not
     */
    public E peek() {
        if (size() == 0)
            return null;
        else
            return theQueue.getFirst();
    }

    /**** BEGIN EXERCISE ****/
    public boolean add(E item) {
        return offer(item);
    }

    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException();
        return poll();
    }

    public E element() {
        if (isEmpty())
            throw new NoSuchElementException();
        return peek();
    }

    public int size() {
        return theQueue.size();
    }

    public boolean isEmpty() {
        return theQueue.size() == 0;
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public boolean contains(Object obj) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public boolean remove(Object obj) {
        return false;
    }

    public Object[] toArray() {
        return null;
    }

    public Object[] toArray(Object[] a) {
        return null;
    }

    public void clear() {

    }
    public Iterator < E > iterator() {
        return null;
    }

    public static void main(String[] args){
        QueueList<Integer> q=new QueueList<>();

        for (int i = 0; i < 5; i++) {
            q.add(i);
        }
        System.out.println(q.size());
        for (int i = 0; i < q.size(); i++) {
            System.out.println(q.get(i));
        }
    }
}
