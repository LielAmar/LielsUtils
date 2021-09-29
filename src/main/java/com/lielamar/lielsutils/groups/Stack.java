package com.lielamar.lielsutils.groups;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Stack<T> {

    private @Nullable Node<T> head;

    public Stack() {
        this.head = null;
    }

    public void push(@NotNull T x) {
        Node<T> temp = new Node<>(x);
        temp.setNext(head);
        head = temp;
    }

    public @Nullable T pop() {
        if(head == null) return null;

        T x = head.getValue();
        head = head.getNext();

        return x;
    }

    public @Nullable T top() {
        if(head == null) return null;

        return head.getValue();
    }

    public boolean isEmpty() {
        return head == null;
    }
}