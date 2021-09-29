package com.lielamar.lielsutils.predicate;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class PredicateUtils {

	public static @NotNull <T> Predicate<T> not(@NotNull Predicate<T> t) {
        return t.negate();
    }
}
