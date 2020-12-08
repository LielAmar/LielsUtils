package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

public class DoubleValidation implements Validation {

    private final double value;
    private final String message;
    private Predicate<Double> predicate;

    @Deprecated
    private double min, max;

    @Deprecated
    private boolean hasMin, hasMax;


    public DoubleValidation(double value) {
        this(value, "", null);
    }

    public DoubleValidation(double value, String message) {
        this(value, message, null);
    }

    public DoubleValidation(double value, Predicate<Double> predicate) {
        this(value, "", predicate);
    }

    public DoubleValidation(double value, String message, Predicate<Double> predicate) {
        this.value = value;
        this.message = message;
        this.predicate = predicate;
    }


    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean validate() {
        return this.predicate == null || this.predicate.test(this.value);
    }


    @Deprecated
    public DoubleValidation(double value, String message, int min) {
        this(value, message, min, 0);
        this.hasMin = true;
        this.hasMax = false;
    }

    @Deprecated
    public DoubleValidation(double value, String message, int min, int max) {
        this.value = value;
        this.message = message;
        this.min = min;
        this.max = max;
        this.hasMin = this.hasMax = true;
    }

    @Deprecated
    public boolean validateModule() {
        if(!this.hasMin && !this.hasMax) return true;

        if(this.hasMin && !this.hasMax && this.value >= this.min) return true;
        if(!this.hasMin && this.value <= this.max) return true;

        return (this.value >= this.min && this.value <= this.max);
    }
}