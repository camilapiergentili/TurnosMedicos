package ar.com.dontar.demo.validation.validator;

import ar.com.dontar.demo.validation.annotation.ValidString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<ValidString, String> {

    private static final String REGEX = "^[a-zA-Z]{3,}$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.trim().isEmpty()) return false;
        return s.matches(REGEX);
    }
}
