package ar.com.dontar.demo.validation.annotation;

import ar.com.dontar.demo.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword{
    String message() default "La contraseña debe contener más de 8 caracteres, al menos una minuscula, una mayuscula y un número";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
