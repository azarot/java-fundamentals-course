package com.bobocode.cs;


import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}.
 *
 * @param <T> generic type parameter
 * @author Taras Boychuk
 * @author Serhii Hryhus
 */
public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        Node<T> next;
        T element;

        public Node(T element) {
            this.element = element;
        }
    }

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    @SafeVarargs
    public static <T> LinkedList<T> of(T... elements) {
        LinkedList<T> list = new LinkedList<>();
        Arrays.stream(elements).forEach(list::add);
        return list;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            var tmp = new Node<T>(element);
            tail.next = tmp;
            tail = tmp;
        }
        size++;
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        var tmp = new Node<T>(element);
        if (index == 0) {
            tmp.next = head;
            head = tmp;
            if (size() == 0) tail = head;

        } else {
            Node<T> node = getNode(index - 1);

            tmp.next = node.next;
            node.next = tmp;
        }
        size++;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        getNode(index).element = element;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    private Node<T> getNode(int index) {
        var tmp = head;
        if (tmp == null) throw new IndexOutOfBoundsException();

        for (int i = 1; i <= index; i++) {
            tmp = tmp.next;
            if (tmp == null) throw new IndexOutOfBoundsException();
        }
        return tmp;
    }

    /**
     * Returns the first element of the list. Operation is performed in constant time O(1)
     *
     * @return the first element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getFirst() {
       if (isEmpty()) throw new NoSuchElementException();
       return head.element;
    }

    /**
     * Returns the last element of the list. Operation is performed in constant time O(1)
     *
     * @return the last element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getLast() {
        if (isEmpty()) throw new NoSuchElementException();

        return tail.element;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return deleted element
     */
    @Override
    public T remove(int index) {
        if (size() == 0) throw new IndexOutOfBoundsException();
        if (index == 0) {
            var tmp = head;
            head = head.next;
            tmp.next = null;
            if (size <= 2) tail = head;
            size--;

            return tmp.element;
        } else {
            var node = getNode(index - 1);

            if (node.next == null) throw new IndexOutOfBoundsException();

            var tmp = node.next;
            node.next = node.next.next;
            tmp.next = null;
            size--;

            return tmp.element;
        }
    }


    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        var tmp = head;
        while (tmp != null) {
            if (tmp.element.equals(element)) return true;

            tmp = tmp.next;
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
