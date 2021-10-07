package com.lielamar.lielsutils.groups;

public class Triple<A, B, C> extends Pair<A, B> {

    private C c;

    public Triple(A a, B b, C c) {
    	super(a, b);
        this.c = c;
    }

    public C getC() { return c; }
    public void setC(C c) { this.c = c; }
}