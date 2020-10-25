package com.lielamar.lielsutils.modules;

public class Pair<K, V> {

    private K k;
    private V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getKey() { return this.k; }
    public V getValue() { return this.v; }

    public void setKey(K k) { this.k = k; }
    public void setValue(V v) { this.v = v; }

}
