package com.lielamar.lielsutils.modules;

public class Stack<T> {

    private Node<T> head;

    public Stack() {
        this.head = null;
    }

    public void push(T x) {
        Node<T> temp = new Node<T>(x);
        temp.setNext(head);
        head = temp;
    }

    public T pop() {
        T x = head.getValue();
        head = head.getNext();

        return x;
    }

    public T top() {
        return head.getValue();
    }

    public boolean isEmpty() {
        return head == null;
    }
}