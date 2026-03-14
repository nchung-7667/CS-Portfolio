package CommonUtils;

import java.awt.*;

/**
 * @implNote implement a queue using a circular array with initial capacity 8.
 *
 * Implement BetterQueueInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Queue and any subclass
 * (including LinkedList, for example) and any other java.util.* library EXCEPT java.util.Objects.
 * Write your own implementation of a Queue.
 *
 * Another great example of why we are implementing our own queue here is that
 * our queue is actually FASTER than Java's LinkedList (our solution is 2x faster!). This is due
 * to a few reasons, the biggest of which are 1. the overhead associated with standard library
 * classes, 2. the fact that Java's LinkedList doesn't store elements next to each other, which
 * increases memory overhead for the system, and 3. LinkedList stores 2 pointers with each element,
 * which matters when you store classes that aren't massive (because it increases the size of each
 * element, making more work for the system).
 *
 * @param <E> the type of object this queue will be holding
 */
public class BetterQueue<E> implements BetterQueueInterface<E> {

    /**
     * Initial size of queue.  Do not decrease capacity below this value.
     */
    private final int INIT_CAPACITY = 8;


    /**
     * If the array needs to increase in size, it should be increased to
     * old capacity * INCREASE_FACTOR.
     *
     * If it cannot increase by that much (old capacity * INCREASE_FACTOR > max int),
     * it should increase by CONSTANT_INCREMENT.
     *
     * If that can't be done either throw OutOfMemoryError()
     *
     */
    private final int INCREASE_FACTOR = 2;
    private final int CONSTANT_INCREMENT = 1 << 5; // 32



    /**
     * If the number of elements stored is < capacity * DECREASE_FACTOR, it should decrease
     * the capacity of the UDS to max(capacity * DECREASE_FACTOR, initial capacity).
     *
     */
    private final double DECREASE_FACTOR = 0.5;


    /**
     * Array to store elements in (according to the implementation
     * note in the class header comment).
     *
     * Circular arrays work as follows:
     *
     *   1. Removing an element increments the "first" index
     *   2. Adding an element inserts it into the next available slot. Incrementing
     *      the "last" index WRAPS to the front of the array, if there are spots available
     *      there (if we have removed some elements, for example).
     *   3. The only way to know if the array is full is if the "last" index
     *      is right in front of the "first" index.
     *   4. If you need to increase the size of the array, put the elements back into
     *      the array starting with "first" (i.e. "first" is at index 0 in the new array).
     *   5. No other implementation details will be given, but a good piece of advice
     *      is to draw out what should be happening in each operation before you code it.
     *
     *   hint: modulus might be helpful
     */
    private E[] queue;
    private int head;
    private int tail;
    private int queueLength;

    /**
     * Constructs an empty queue
     */
    @SuppressWarnings("unchecked")
    public BetterQueue(){
        queue = (E[]) new Object[INIT_CAPACITY];
        head = 0;
        tail = -1;
        queueLength = 0;
    }

    /**
     * Add an item to the back of the queue
     *
     * @param item item to push
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (queueLength == queue.length) {//Check if the queue is full
            int newSize;
            if (queue.length  < Integer.MAX_VALUE / INCREASE_FACTOR) {
                newSize = queueLength * INCREASE_FACTOR;
            }
            else if (queue.length + CONSTANT_INCREMENT < Integer.MAX_VALUE) {
                newSize = queue.length + CONSTANT_INCREMENT;
            }
            else {
                throw new OutOfMemoryError();
            }
            E[] expandedQueue = (E[]) new Object[newSize];
            int index = 0;
            for (int i = head; i < queue.length; i++) {
                expandedQueue[index] = queue[i];
                index++;
            }
            if(tail < head) {
                for (int i = 0; i < head; i++) {
                    expandedQueue[index] = queue[i];
                    index++;
                }
            }
            queue = expandedQueue;
            head = 0;
            tail = queueLength - 1;
        }
        tail++;
        if (tail == queue.length) {
            tail = 0;
        }
        queue[tail] = item;
        queueLength++;
    }

    /**
     * Returns the front of the queue (does not remove it) or <code>null</code> if the queue is empty
     *
     * @return front of the queue or <code>null</code> if the queue is empty
     */
    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return queue[head];
    }

    /**
     * Returns and removes the front of the queue
     *
     * @return the head of the queue, or <code>null</code> if this queue is empty
     */
    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        E item = queue[head];
        queueLength--;
        head++;
        if (head == queue.length) { //Check if head wraps around
            head = 0;
        }
        if (queueLength < queue.length * DECREASE_FACTOR && queue.length > INIT_CAPACITY) { //Check if array needs to shrink
            double newSize = queue.length * DECREASE_FACTOR;
            E[] decreasedQueue = (E[]) new Object[(int) newSize];
            int index = 0;
            if (head < tail) {
                for (int i = head; i < tail + 1; i++) {
                    decreasedQueue[index] = queue[i];
                    index++;
                }
            }
            else {
                for (int i = head; i < queue.length; i++) {
                    decreasedQueue[index] = queue[i];
                    index++;
                }
                for (int i = 0; i < tail; i++) {
                    decreasedQueue[index] = queue[i];
                    index++;
                }
            }
            queue = decreasedQueue;
            head = 0;
            tail = queueLength - 1;
        }
        return item;
    }

    /**
     * Returns the number of elements in the queue
     *
     * @return integer representing the number of elements in the queue
     */
    @Override
    public int size() {
        return queueLength;
    }

    /**
     * Returns whether the queue is empty
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (queueLength == 0) {
            return true;
        }
        return false;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the queue how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
