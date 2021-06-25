package com.lielamar.lielsutils.validation;

import java.util.function.Predicate;

public abstract class AbstractValidation<T> implements Validation<T> {
	
	protected final T value;
    private final String message;
    private Predicate<T> predicate;
    
    protected AbstractValidation(T value) {
        this(value, "", null);
    }

    protected AbstractValidation(T value, String message) {
        this(value, message, null);
    }

    protected AbstractValidation(T value, Predicate<T> predicate) {
        this(value, "", predicate);
    }

    protected AbstractValidation(T value, String message, Predicate<T> predicate) {
        this.value = value;
        this.message = message;
        this.predicate = predicate;
    }
    
	@Override
	public T getValue() {
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
}
