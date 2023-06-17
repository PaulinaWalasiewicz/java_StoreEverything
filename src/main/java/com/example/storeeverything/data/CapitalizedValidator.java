package com.example.storeeverything.data;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CapitalizedValidator implements ConstraintValidator<Capitalized, String> {
    @Override
    public void initialize(Capitalized constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        char firstChar = value.charAt(0);
        return Character.isUpperCase(firstChar);
    }
}