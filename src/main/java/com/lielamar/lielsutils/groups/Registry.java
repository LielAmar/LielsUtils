package com.lielamar.lielsutils.groups;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Registry<T> {

    protected Map<String, T> data;

    public Registry() {
        data = new HashMap<>();
    }


    public void register(String key, T item) {
        data.put(key, item);
    }

    public T get(String key) {
        return data.get(key);
    }

    public T remove(String key) {
        return data.remove(key);
    }


    public Set<String> getExtendsKey(Class<? extends T> clazz) {
        return data.entrySet().stream().filter(stringTEntry -> clazz.isInstance(stringTEntry.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public Set<String> getAllKeys() {
        return data.keySet();
    }
}