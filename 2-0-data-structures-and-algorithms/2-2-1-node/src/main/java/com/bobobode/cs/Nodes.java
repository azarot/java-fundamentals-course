package com.bobobode.cs;

import com.bobocode.util.ExerciseNotCompletedException;
import lombok.NonNull;

/**
 * A class that consists of static methods only and provides util methods for {@link Node}.
 *
 * @author Taras Boychuk
 */
public class Nodes {
    private Nodes() {
    }

    /**
     * Creates a new instance of {@link Node} that holds provided element
     *
     * @param element any element of type T
     * @param <T>     generic type
     * @return a new instance of {@link Node}
     */
    public static <T> Node<T> create(T element) {
        return new Node<>(element);
    }

    /**
     * Create a connection between first and second nodes, so object first stores a reference to the second.
     *
     * @param first  any {@link Node} object
     * @param second any {@link Node} object
     * @param <T>    a genetic type
     */
    public static <T> void link(Node<T> first, Node<T> second) {
        first.next = second;
    }

    /**
     * Creates two new {@link Node} objects using provided firstElement and secondElement, and create a connection
     * between those two elements so the first node will hold a reference to a second one.
     *
     * @param firstElement  any element of type T
     * @param secondElement any element of type T
     * @param <T>           a genetic type
     * @return a reference to a first node created based on firstElement
     */
    public static <T> Node<T> pairOf(T firstElement, T secondElement) {
        var first = new Node<>(firstElement);
        var second = new Node<>(secondElement);
        link(first, second);
        return first;
    }

    /**
     * Creates two new {@link Node} objects using provided firstElement and secondElement, and creates connections
     * between those nodes so the first node will hold a reference to a second one, and a second node will hold
     * a reference to the first one.
     *
     * @param firstElement  any element of type T
     * @param secondElement any element of type T
     * @param <T>           generic type T
     * @return a reference to the first node
     */
    public static <T> Node<T> closedPairOf(T firstElement, T secondElement) {
        var first = pairOf(firstElement, secondElement);
        first.next.next = first;
        return first;
    }

    /**
     * Creates a linked chain of {@link Node} objects based on provided elements. Creates a connection between those
     * nodes so each node will hold a reference to the next one in the chain. HINT: it's basically a linked list.
     *
     * @param elements a array of elements of type T
     * @param <T>      generic type T
     * @return a reference to the first element of the chain
     */
    @SafeVarargs
    public static <T> Node<T> chainOf(T... elements) {
        Node<T> first = new Node<>(elements[0]);
        var tmp = first;
        for (int i = 1; i < elements.length; i++) {
            tmp.next = new Node<>(elements[i]);
            tmp = tmp.next;
        }

        return first;
    }

    /**
     * Creates a linked circle of {@link Node} objects based on provided elements. Creates a connection between those
     * nodes so each node will hold a reference to the next one in the chain, and the last one will hold a reference to
     * the first one.
     *
     * @param elements a array of elements of type T
     * @param <T>      generic type T
     * @return a reference to the first element of the chain
     */
    @SafeVarargs
    public static <T> Node<T> circleOf(T... elements) {
        var first = chainOf(elements);
        var last = first;
        while (last.next != null) {
            last = last.next;
        }
        last.next = first;

        return first;
    }
}
