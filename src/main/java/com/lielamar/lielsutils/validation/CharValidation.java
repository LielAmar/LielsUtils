package com.lielamar.lielsutils.validation;

public class CharValidation implements Validation {

    private final char value;
    private final String message;

    private final Character[] allowedCharacters;
    private boolean hasAllowedCharacters;

    public CharValidation(char value) {
        this(value, "", null);
        this.hasAllowedCharacters = false;
    }

    public CharValidation(char value, String message) {
        this(value, message, null);
        this.hasAllowedCharacters = false;
    }

    public CharValidation(char value, String message, Character[] allowedCharacters) {
        this.value = value;
        this.message = message;
        this.allowedCharacters = allowedCharacters;
        this.hasAllowedCharacters = true;
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
        if(!this.hasAllowedCharacters || this.allowedCharacters == null) return true;

        for(Character c : this.allowedCharacters)
            if(c == this.value) return true;

        return false;
    }
}