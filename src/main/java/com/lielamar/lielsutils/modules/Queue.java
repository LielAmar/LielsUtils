package com.lielamar.lielsutils.modules;

public class Queue<T> {

    private Node<T> first;
    private Node<T> last;

    public Queue() {
        this.first = null;
        this.last = null;
    }

    public void add(T x) {
        Node<T> temp = new Node<>(x);

        if(first == null)
            first = temp;
        else
            last.setNext(temp);

        last = temp;
    }

    public T remove() {
        if(first == null)
            return null;

        T x = first.getValue();

        first = first.getNext();

        if(first == null)
            last = null;

        return x;
    }

    public T element() {
        if(first == null)
            return null;

        return first.getValue();
    }

    public boolean isEmpty() {
        return first == null;
    }
}