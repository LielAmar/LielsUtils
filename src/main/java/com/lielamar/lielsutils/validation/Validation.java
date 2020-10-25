package com.lielamar.lielsutils.validation;

import java.util.ArrayList;
import java.util.List;

public class Validation<T> {

    private T value;

    private boolean hasMin, hasMax;
    private int min, max;

    private boolean hasAcceptedChars;
    private List<Character> acceptedChars;

    private String violationMessage;

    public Validation(T value) {
        this(value, "", 0, 0, null);
        this.hasMin = this.hasMax = this.hasAcceptedChars = false;
    }

    public Validation(T value, String violationMessage) {
        this(value, violationMessage, 0, 0, null);
        this.hasMin = this.hasMax = this.hasAcceptedChars = false;
    }

    public Validation(T value, String violationMessage, List<Character> acceptedChars) {
        this(value, violationMessage, 0, 0, acceptedChars);
        this.hasMin = this.hasMax = false;
        this.hasAcceptedChars = true;
    }

    public Validation(T value, String violationMessage, int min) {
        this(value, violationMessage, min, 0, null);
        this.hasMax = this.hasAcceptedChars = false;
        this.hasMin = true;
    }

    public Validation(T value, String violationMessage, int min, int max) {
        this(value, violationMessage, min, max, null);
        this.hasMin = this.hasMax = true;
        this.hasAcceptedChars = false;
    }

    public Validation(T value, String violationMessage, int min, int max, List<Character> acceptedChars) {
        this.value = value;
        this.violationMessage = violationMessage;
        this.min = min;
        this.max = max;
        this.acceptedChars = acceptedChars;

        this.hasMin = this.hasMax = this.hasAcceptedChars = true;
    }


    public T getValue() {
        return this.value;
    }

    public String getViolationMessage() {
        return this.violationMessage;
    }

    public boolean validate() {
        if(this.value instanceof Integer) {
            if(!this.hasMin && !this.hasMax) return true;
            if(this.hasMin && !this.hasMax && (int) this.value >= min) return true;
            if(!this.hasMin && this.hasMax && (int) this.value <= max) return true;
            return (int) this.value >= min && (int) this.value <= max;
        }

        if(this.value instanceof Character) {
            if(!this.hasAcceptedChars) return true;
            return this.acceptedChars.contains(this.value);
        }

        return false;
    }


    public static List<Validation<?>> validateParameters(List<Validation<?>> parameters) {
        List<Validation<?>> violations = new ArrayList<>();

        for(Validation<?> parameter : parameters) {
            if(!parameter.validate())
                violations.add(parameter);
        }

        return violations;
    }
}
