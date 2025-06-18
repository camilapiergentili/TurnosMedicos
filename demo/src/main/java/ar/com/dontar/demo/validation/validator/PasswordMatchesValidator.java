package ar.com.dontar.demo.validation.validator;


import ar.com.dontar.demo.validation.PasswordConfirmation;
import ar.com.dontar.demo.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordConfirmation> {

    @Override
    public boolean isValid(PasswordConfirmation userDto, ConstraintValidatorContext context) {
        if(userDto.getPassword() == null || userDto.getConfirmPassword() == null){
            return false;
        }

        boolean valid = userDto.getPassword().equals(userDto.getConfirmPassword());

        if(!valid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La contraseña no coinciden")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return valid;
    }
}
