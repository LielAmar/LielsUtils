package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

public interface Validation<T> extends Predicate<T> {

	String getErrorMessage(T filteredObject);
}