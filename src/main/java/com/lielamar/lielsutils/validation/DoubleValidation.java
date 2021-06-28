package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

import com.lielamar.lielsutils.validation.numerical.NumericalValidation;

public class DoubleValidation extends NumericalValidation<Double> {

    public DoubleValidation(double value) {
        super(value);
    }

    public DoubleValidation(double value, String message) {
    	super(value, message);
    }

    public DoubleValidation(double value, Predicate<Double> predicate) {
        super(value, predicate);
    }

    public DoubleValidation(double value, String message, Predicate<Double> predicate) {
    	super(value, message, predicate);
    }
    
    @Deprecated
    public DoubleValidation(double value, String message, int min) {
        this(value, message, min, 0);
    }

    @Deprecated
    public DoubleValidation(double value, String message, int min, int max) {
    	this(value, message);
    }
}