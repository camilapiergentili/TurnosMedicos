package ar.com.dontar.demo.validation.validator;


import ar.com.dontar.demo.controller.dto.UserDto;
import ar.com.dontar.demo.validation.annotation.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
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
