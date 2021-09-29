package com.lielamar.lielsutils.groups;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Node<T> {

    private @NotNull T value;
    private @Nullable Node<T> next;

    public Node(@NotNull T value) {
        this.value = value;
        this.next = null;
    }

    public Node(@NotNull T value, @Nullable Node<T> next) {
        this.value = value;
        this.next = next;
    }

    public @NotNull T getValue() { return this.value; }
    public void setValue(@NotNull T value) { this.value = value; }

    public @Nullable Node<T> getNext() { return next; }
    public void setNext(@Nullable Node<T> next) { this.next = next; }


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
