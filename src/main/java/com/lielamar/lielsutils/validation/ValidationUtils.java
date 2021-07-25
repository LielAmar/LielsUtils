package com.lielamar.lielsutils.validation;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ValidationUtils {
	
	//Container of static methods
	private ValidationUtils(){}
	
	/**
	 * Returns the violations(failed validations) out of the provided {@code validations} for the provided {@code object}.
	 * 
	 * @param <T> The type of the object.
	 * @param object The object to check against.
	 * @param validations The validations to check.
	 * @return The violations out of the provided validations.
	 */
	public static <T> List<Validation<T>> getViolationsFor(T object, Iterable<Validation<T>> validations) {
		return StreamSupport.stream(validations.spliterator(), false)
				.filter(validation -> !validation.test(object))
				.collect(toList());
	}

	public static <T> Stream<String> buildErrorMessages(T object, Iterable<Validation<T>> validations) {
		return getViolationsFor(object, validations).stream()
				.map(validation -> validation.getErrorMessage(object));
	}
}
