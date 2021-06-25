package com.lielamar.lielsutils.validation.numerical;

import java.util.function.Predicate;

import com.lielamar.lielsutils.validation.AbstractValidation;

public abstract class NumericalValidation<N extends Number> extends AbstractValidation<N>
{
	@Deprecated
    private double min, max;

    @Deprecated
    private boolean hasMin, hasMax;

    protected NumericalValidation(N value) {
        super(value);
    }

    protected NumericalValidation(N value, String message) {
    	super(value, message);
    }

    protected NumericalValidation(N value, Predicate<N> predicate) {
        super(value, predicate);
    }

    protected NumericalValidation(N value, String message, Predicate<N> predicate) {
    	super(value, message, predicate);
    }

    @Deprecated
    protected NumericalValidation(N value, String message, double min) {
        this(value, message, min, 0);
        this.hasMin = true;
        this.hasMax = false;
    }

    @Deprecated
    protected NumericalValidation(N value, String message, double min, double max) {
    	super(value, message);
        this.min = min;
        this.max = max;
        this.hasMin = this.hasMax = true;
    }

    @Deprecated
    public boolean validateModule() {
        if(!this.hasMin && !this.hasMax) return true;
        
        double valueDouble = this.value.doubleValue();

        if(this.hasMin && !this.hasMax && valueDouble >= this.min) return true;
        if(!this.hasMin && valueDouble <= this.max) return true;

        return (valueDouble >= this.min && valueDouble <= this.max);
    }
}
