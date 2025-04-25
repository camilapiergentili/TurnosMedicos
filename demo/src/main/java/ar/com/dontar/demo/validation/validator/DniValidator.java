package ar.com.dontar.demo.validation.validator;

import ar.com.dontar.demo.validation.annotation.ValidDniLong;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DniValidator implements ConstraintValidator<ValidDniLong, Long> {

    private static final String DNI_PATTERN = "^[0-9]{8}$";

    @Override
    public boolean isValid(Long dni, ConstraintValidatorContext constraintValidatorContext) {
       if(dni == null) return false;
       return String.valueOf(dni).matches(DNI_PATTERN);
    }
}
