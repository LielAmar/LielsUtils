package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

public class CharValidation implements Validation {

    private final char value;
    private final String message;
    private Predicate<Character> predicate;

    @Deprecated
    private Character[] allowedCharacters;

    @Deprecated
    private boolean hasAllowedCharacters;


    public CharValidation(char value) {
        this(value, (Predicate<Character>) null);
    }

    public CharValidation(char value, String message) {
        this(value, message, (Predicate<Character>) null);
    }

    public CharValidation(char value, Predicate<Character> predicate) {
        this(value, "", predicate);
    }

    public CharValidation(char value, String message, Predicate<Character> predicate) {
        this.value = value;
        this.message = message;
        this.predicate = predicate;
    }


    @Override
    public Character getValue() {
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
    public CharValidation(char value, String message, Character[] allowedCharacters) {
        this.value = value;
        this.message = message;
        this.allowedCharacters = allowedCharacters;
        this.hasAllowedCharacters = true;
    }

    @Deprecated
    public boolean validateModule() {
        if(!this.hasAllowedCharacters || this.allowedCharacters == null) return true;

        for(Character c : this.allowedCharacters)
            if(c == this.value) return true;

        return false;
    }
}