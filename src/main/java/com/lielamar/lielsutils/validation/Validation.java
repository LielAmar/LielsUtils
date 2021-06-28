package com.lielamar.lielsutils.validation;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public interface Validation<T> {

    T getValue();
    String getMessage();
    boolean validate();

    static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }

    /**
     * Returns a list of violations (validations that failed)
     *
     * @param parameters   List of validations to check
     * @return             List of violations
     */
    static List<Validation<?>> validateParameters(List<Validation<?>> parameters) {
    	return parameters.stream()
    			.filter(not(Validation::validate))
    			.collect(toList());
    }

	static List<Validation<?>> validateParameters(Validation<?>... parameters) {
    	List<Validation<?>> list = Arrays.asList(parameters);
    	return validateParameters(list);
    }
}