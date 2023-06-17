package com.example.storeeverything.data;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CapitalizedValidator.class)
@Documented
public @interface Capitalized {
    String message() default "Value must start with an uppercase letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
