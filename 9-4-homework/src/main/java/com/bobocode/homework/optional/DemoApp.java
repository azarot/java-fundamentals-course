package com.bobocode.homework.optional;

public class DemoApp {
    public static void main(String[] args) {
        Node<Integer> linkedList = createLinkedList(1, 3, 5, 6, 9, 11, 13, 15);
        printLinkedList(linkedList);
        Node<Integer> reverse = reverse(linkedList);
        printLinkedList(reverse);
    }

    /**
     * Creates a linked list based on the input array and returns a head
     */
    public static <T> Node<T> createLinkedList(T... element) {
        Node<T> head = null;
        for (int i = element.length - 1; i >= 0 ; i--) {
            var node = new Node<>(element[i]);
            if (head != null) {
                node.next = head;
            }
            head = node;
        }
        return head;
    }

    /**
     * Prints a linked list with arrows like this
     * head:5 -> 7 -> 1 -> 4
     *  7 -> 5
     * @param head the first element of the list
     */
    public static void printLinkedList(Node<?> head) {
        Node<?> tmp = head;
        while (tmp != null) {
            System.out.print(tmp.element);
            if (tmp.next != null) {
                System.out.print(" -> ");
            }
            tmp = tmp.next;
        }
        System.out.println();
    }

    public static<T> Node<T> reverseLinkedList(Node<T> head) {
        int size = getSize(head);

        for (int i = 0; i < size - 1; i++) {
            int changes = size - i;
            var tmp = head;
            head = tmp.next;
            Node<T> prev = null;
            while (--changes > 0) {
                Node<T> next = tmp.next;
                tmp.next = next.next;
                next.next = tmp;
                if (prev != null) {
                    prev.next = next;
                }
                prev = next;
            }
        }

        return head;
    }

    public static <T> Node<T> reverse(Node<T> head) {
        Node<T> curr = head;
        Node<T> next = null;
        Node<T> prev = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private static int getSize(Node<?> head) {
        int size = 0;
        while (head != null) {
            head = head.next;
            size++;
        }
        return size;
    }
}