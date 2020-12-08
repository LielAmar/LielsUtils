package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

public class IntValidation implements Validation {

    private final int value;
    private final String message;
    private Predicate<Integer> predicate;

    @Deprecated
    private int min, max;

    @Deprecated
    private boolean hasMin, hasMax;


    public IntValidation(int value) {
        this(value, "", null);
    }

    public IntValidation(int value, String message) {
        this(value, message, null);
    }

    public IntValidation(int value, Predicate<Integer> predicate) {
        this(value, "", predicate);
    }

    public IntValidation(int value, String message, Predicate<Integer> predicate) {
        this.value = value;
        this.message = message;
        this.predicate = predicate;
    }


    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public boolean validate() {
        return this.predicate == null || this.predicate.test(this.value);
    }


    @Deprecated
    public IntValidation(int value, String message, int min) {
        this(value, message, min, 0);
        this.hasMin = true;
        this.hasMax = false;
    }

    @Deprecated
    public IntValidation(int value, String message, int min, int max) {
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