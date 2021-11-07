package com.lielamar.lielsutils.groups;

import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Registry<T> {

	protected final Map<String, T> data;

    public Registry() {
        data = new HashMap<>();
    }


	public void register(String key, T item) {
		data.put(key, item);
	}

	public Optional<T> get(String key) {
		return Optional.ofNullable(data.get(key));
	}

	public Optional<T> remove(String key) {
		return Optional.ofNullable(data.remove(key));
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