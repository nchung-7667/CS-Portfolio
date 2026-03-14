package CommonUtils;

import java.util.EmptyStackException;

/**
 * @implNote Implement a stack using an array with initial capacity 8.
 *
 * Implement BetterStackInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Stack and any
 * other java.util.* library EXCEPT java.util.EmptyStackException and java.util.Arrays.
 * Write your own implementation of a Stack.
 *
 *
 * @param <E> the type of object this stack will be holding
 */
public class BetterStack<E> implements BetterStackInterface<E> {

    /**
     * Initial size of stack.  Do not decrease capacity below this value.
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
     */
    private E[] stack;
    private int top;
    private int bot;
    private int stackLength;

    /**
     * Constructs an empty stack
     */
    @SuppressWarnings("unchecked")
    public BetterStack(){
        stack = (E[]) new Object[INIT_CAPACITY];
        top = -1;
        bot = 0;
        stackLength = 0;
    }


    /**
     * Push an item onto the top of the stack
     *
     * @param item item to push
     * @throws OutOfMemoryError if the underlying data structure cannot hold any more elements
     */
    @Override
    public void push(E item) throws OutOfMemoryError {
        /*if (item == null) {
            throw new NullPointerException();
        }*/
        top++;
        if (top == stack.length) {
            int newSize;
            if (stack.length < Integer.MAX_VALUE / INCREASE_FACTOR) {
                newSize = stack.length * INCREASE_FACTOR;
            }
            else if (stack.length + CONSTANT_INCREMENT < Integer.MAX_VALUE) {
                newSize = stack.length + CONSTANT_INCREMENT;
            }
            else {
                throw new OutOfMemoryError();
            }
            E[] expandedStack = (E[]) new Object[newSize];
            for (int i = 0; i < top; i++) {
                expandedStack[i] = stack[i];
            }
            stack = expandedStack;
        }
        stack[top] = item;
        stackLength++;
    }

    /**
     * Remove and return the top item on the stack
     *
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E item = stack[top];
        top--;
        stackLength--;
        if (stackLength < stack.length * DECREASE_FACTOR && stack.length > INIT_CAPACITY) {
            double newSize = stack.length * DECREASE_FACTOR;
            E[] decreasedStack = (E[]) new Object[(int) newSize];
            for (int i = 0; i <= top; i++) {
                decreasedStack[i] = stack[i];
            }
            stack = decreasedStack;
        }
        return item;
    }

    /**
     * Returns the top of the stack (does not remove it).
     *
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E peek() {
        if (isEmpty() == true) {
            throw new EmptyStackException();
        }
        return stack[top];
    }

    /**
     * Returns whether the stack is empty
     *
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (stackLength == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of elements in the stack
     *
     * @return integer representing the number of elements in the stack
     */
    @Override
    public int size() {
        return stackLength;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(java.awt.Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the stack how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
