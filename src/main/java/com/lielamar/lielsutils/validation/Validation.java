package com.lielamar.lielsutils.validation;

import static com.lielamar.lielsutils.PredicateUtils.not;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public interface Validation<T> {

    T getValue();
    String getMessage();
    boolean validate();

    /**
     * Returns a list of violations (validations that failed)
     *
     * @param parameters   List of validations to check
     * @return             List of violations
     */
    public static List<Validation<?>> validateParameters(List<Validation<?>> parameters) {
    	return parameters.stream()
    			.filter(not(Validation::validate))
    			.collect(toList());
    }
    
    public static List<Validation<?>> validateParameters(Validation<?>... parameters) {
    	List<Validation<?>> list = Arrays.asList(parameters);
    	return validateParameters(list);
    }
}