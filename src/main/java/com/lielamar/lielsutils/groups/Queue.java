package com.lielamar.lielsutils.groups;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Queue<T> {

    private @Nullable Node<T> first;
    private @Nullable Node<T> last;

    public Queue() {
        this.first = null;
        this.last = null;
    }

    public void add(@NotNull T x) {
        Node<T> temp = new Node<>(x);

        if(first == null)
            first = temp;
        else {
            if(last != null)
                last.setNext(temp);
        }

        last = temp;
    }

    public @Nullable T remove() {
        if(first == null)
            return null;

        T x = first.getValue();

        first = first.getNext();

        if(first == null)
            last = null;

        return x;
    }

    public @Nullable T element() {
        if(first == null)
            return null;

        return first.getValue();
    }

    public boolean isEmpty() {
        return first == null;
    }
}