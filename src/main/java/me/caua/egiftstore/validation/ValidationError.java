package me.caua.egiftstore.validation;

import me.caua.egiftstore.util.Error;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends Error {

    record FieldError(String fieldName, String message) {}

    private List<FieldError> errors = null;

    public ValidationError(String code, String message) {
        super (code, message);
    }

    public void addFieldError(String fieldName, String message) {
        if (errors == null) errors = new ArrayList<>();
        errors.add(new FieldError(fieldName, message));
    }

    public List<FieldError> getErrors() {
        return errors.stream().toList();
    }

}
