package com.lielamar.lielsutils.validation;

import static com.google.common.base.Predicates.not;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public interface Validation<T> {

    T getValue();
    String getMessage();
    boolean validate();

    static List<Validation<?>> validateParameters(List<Validation<?>> parameters) {
    	return parameters.stream()
    			.filter(not(Validation::validate))
    			.collect(toList());
    }

    @SafeVarargs
	static List<Validation<?>> validateParameters(Validation<?>... parameters) {
    	List<Validation<?>> list = Arrays.asList(parameters);
    	
    	return validateParameters(list);
    }
}