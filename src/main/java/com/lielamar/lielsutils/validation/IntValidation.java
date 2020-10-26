package com.lielamar.lielsutils.validation;

public class IntValidation implements Validation {

    private final int value;
    private final String message;

    private final int min, max;
    private boolean hasMin, hasMax;

    public IntValidation(int value) {
        this(value, "", 0, 0);
        this.hasMin = this.hasMax = false;
    }

    public IntValidation(int value, String message) {
        this(value, message, 0, 0);
        this.hasMin = this.hasMax = false;
    }

    public IntValidation(int value, String message, int min) {
        this(value, message, min, 0);
        this.hasMin = true;
        this.hasMax = false;
    }

    public IntValidation(int value, String message, int min, int max) {
        this.value = value;
        this.message = message;
        this.min = min;
        this.max = max;
        this.hasMin = this.hasMax = true;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public boolean validate() {
        if(!this.hasMin && !this.hasMax) return true;

        if(this.hasMin && !this.hasMax && this.value >= this.min) return true;
        if(!this.hasMin && this.value <= this.max) return true;

        return (this.value >= this.min && this.value <= this.max);
    }
}