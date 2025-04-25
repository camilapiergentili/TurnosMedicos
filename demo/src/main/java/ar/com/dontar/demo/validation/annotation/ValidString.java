package ar.com.dontar.demo.validation.annotation;

import ar.com.dontar.demo.validation.validator.StringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidString {
    String message() default "El campo debe contar con más de 3 caracteres y no puede tener números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
