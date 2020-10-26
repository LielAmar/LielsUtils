package com.lielamar.lielsutils.validation;

import java.util.ArrayList;
import java.util.List;

public interface Validation {

    Object getValue();
    String getMessage();
    boolean validate();

    static List<Validation> validateParameters(List<Validation> parameters) {
        List<Validation> violations = new ArrayList<>();

        for(Validation parameter : parameters) {
            if(!parameter.validate())
                violations.add(parameter);
        }

        return violations;
    }
}