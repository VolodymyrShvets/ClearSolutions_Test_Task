package com.clearsolutionstesttask.service.agevalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDate {
    String message() default "Your Birth Date must be earlier than current date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
