package com.lielamar.lielsutils.validation;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;

public class CharValidation extends AbstractValidation<Character> {

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
    
    public static CharValidation ofPossibleLetters(char letter, Character... options) {
    	//cached outside the lambda for A) awesome readability B) negligible efficiency
    	Set<Character> allowedLetters = Sets.newHashSet(options);
    	
    	return new CharValidation(letter, allowedLetters::contains);
    }
    
    public static CharValidation ofRange(char letter, char min, char max){
    	return new CharValidation(letter, l -> letter >= min && letter <= max);
    }

    @Deprecated
    public CharValidation(char value, String message, Character... allowedCharacters) {
    	this(value, message, ofPossibleLetters(value, allowedCharacters).predicate);
    }

    @Deprecated
    public boolean validateModule() {
    	return false;
    }
}