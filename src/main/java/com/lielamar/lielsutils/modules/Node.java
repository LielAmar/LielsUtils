package com.lielamar.lielsutils.modules;

import org.jetbrains.annotations.NotNull;

public class Node<T> {

    private T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() { return this.value; }
    public void setValue(T value) { this.value = value; }

    public Node<T> getNext() { return next; }
    public void setNext(Node<T> next) { this.next = next; }


    public static int amountOfNodes(Node<?> node) {
        if(node == null) return 1;
        return 1+amountOfNodes(node.getNext());
    }

    public static Node<?> getLast(@NotNull Node<?> node) {
        while(node.getNext() != null)
            node = node.getNext();
        return node;
    }
}
