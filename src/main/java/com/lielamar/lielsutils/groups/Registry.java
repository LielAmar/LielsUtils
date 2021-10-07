package com.lielamar.lielsutils.groups;

import static java.util.stream.Collectors.toSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Registry<T> {

	protected final Map<String, T> data;

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
		return data.entrySet().stream()
				.filter(stringTEntry -> clazz.isInstance(stringTEntry.getValue()))
				.map(Map.Entry::getKey)
				.collect(toSet());
	}

    public Set<String> getAllKeys() {
		return new HashSet<>(data.keySet());
	}
}