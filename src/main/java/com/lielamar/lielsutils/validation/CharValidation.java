package com.lielamar.lielsutils.validation;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;

public class CharValidation extends AbstractValidation<Character> {

    @Deprecated
    private final Set<Character> allowedCharacters = new HashSet<>();

    public CharValidation(char value) {
        super(value);
    }

    public CharValidation(char value, String message) {
        super(value, message);
    }

    public CharValidation(char value, Predicate<Character> predicate) {
        super(value, predicate);
    }

    public CharValidation(char value, String message, Predicate<Character> predicate) {
        super(value, message, predicate);
    }

    @Deprecated
    public CharValidation(char value, String message, Character... allowedCharacters) {
    	this(value, message);
        this.allowedCharacters.addAll(Sets.newHashSet(allowedCharacters));
    }

    @Deprecated
    public boolean validateModule() {
    	return this.allowedCharacters.stream().anyMatch(c -> c == this.value);
    }
}