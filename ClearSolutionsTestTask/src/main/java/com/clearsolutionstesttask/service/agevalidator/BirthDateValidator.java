package com.clearsolutionstesttask.service.agevalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Value("${age-to-register}")
    private int minimumAgeToRegister;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (!isPersonOlderThanAge(birthDate)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("You must be at least 18 y.o.!")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    public boolean isPersonOlderThanAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);

        // Check if the person's age is greater than or equal to (18) years
        return age.getYears() >= minimumAgeToRegister;
    }
}
