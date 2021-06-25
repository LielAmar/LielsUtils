package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

import com.lielamar.lielsutils.validation.numerical.NumericalValidation;

public class IntValidation extends NumericalValidation<Integer> {

    public IntValidation(int value) {
        super(value);
    }

    public IntValidation(int value, String message) {
    	super(value, message);
    }

    public IntValidation(int value, Predicate<Integer> predicate) {
        super(value, predicate);
    }

    public IntValidation(int value, String message, Predicate<Integer> predicate) {
    	super(value, message, predicate);
    }

    @Deprecated
    public IntValidation(int value, String message, int min) {
        this(value, message, min, 0);
    }

    @Deprecated
    public IntValidation(int value, String message, int min, int max) {
    	super(value, message);
    }
}