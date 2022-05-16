package com.bobocode.homework;

import java.util.Objects;
import java.util.Stack;

public class ReversedDemoApp {
    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head);
        printReversedUsingStack(head);
    }

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */
    @SafeVarargs
    public static <T> Node<T> createLinkedList(T... elements) {
        Objects.requireNonNull(elements);

        if (elements.length == 0) return null;

        Node<T> head = new Node<>(elements[0]);
        Node<T> tail = head;

        for (int i = 1; i < elements.length; i++) {
            var tmp = new Node<>(elements[i]);
            tail.next = tmp;
            tail = tmp;
        }
        return head;
    }

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        printReversedRecursivelyNode(head);
        System.out.println();
    }

    private static <T> void printReversedRecursivelyNode(Node<T> node) {
        if (node.next != null) {
            printReversedRecursivelyNode(node.next);
            System.out.print(" -> " + node.element);
        } else {
            System.out.print(node.element);
        }
    }

    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        Objects.requireNonNull(head);
        var stack = new Stack<T>();
        var tmp = head;
        while (tmp != null) {
            stack.push(tmp.element);
            tmp = tmp.next;
        }

        System.out.print(stack.pop());
        while (!stack.isEmpty()) {
            System.out.print(" -> " + stack.pop());
        }
        System.out.println();
    }
}