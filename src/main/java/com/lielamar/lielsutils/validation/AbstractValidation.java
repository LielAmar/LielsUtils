package com.lielamar.lielsutils.validation;

public abstract class AbstractValidation<T> implements Validation<T> {
	
    private final String errorMessage;
    
    protected AbstractValidation(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
	@Override
	public String getErrorMessage(T filteredObject) {
		return String.format(this.errorMessage, filteredObject);
	}
}