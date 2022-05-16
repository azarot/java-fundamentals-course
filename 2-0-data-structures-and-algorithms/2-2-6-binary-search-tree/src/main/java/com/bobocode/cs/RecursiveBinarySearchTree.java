package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private Node<T> root;
    private int size = 0;

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        public Node(T element) {
            this.element = element;
        }
    }

    @SafeVarargs
    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> result = new RecursiveBinarySearchTree<>();
        Arrays.stream(elements).forEach(result::insert);

        return result;
    }

    @Override
    public boolean insert(T element) {
       if (root == null) {
           this.size++;
           root = new Node<>(element);
           return true;
       }
        return insert(root, element);
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        return contains(root, element);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int depth() {
        int depth = depth(root);
        return depth == 0 ? 0 : depth - 1;
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        traverse(root, consumer);
    }

    private boolean insert(Node<T> node, T element) {
        int compared = node.element.compareTo(element);
        if (compared > 0) {
            if (node.left == null) {
                node.left = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(node.left, element);
            }
        } else if (compared < 0) {
            if (node.right == null) {
                node.right = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(node.right, element);
            }
        } else {
            return false;
        }
    }

    private boolean contains(Node<T> node, T element) {
        if (node == null) return false;

        int compareTo = node.element.compareTo(element);
        if (compareTo > 0) {
            return contains(node.left, element);
        } else if (compareTo < 0) {
            return contains(node.right, element);
        } else {
            return true;
        }
    }

    private void traverse(Node<T> node, Consumer<T> consumer) {
        if (node.left != null) traverse(node.left, consumer);

        consumer.accept(node.element);

        if (node.right != null) traverse(node.right, consumer);
    }

    private int depth(Node<T> node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;

        int leftDepth = depth(node.left);
        int rightDepth = depth(node.right);

        return 1 + Math.max(leftDepth, rightDepth);
    }
}
