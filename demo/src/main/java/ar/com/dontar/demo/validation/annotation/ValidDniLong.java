package ar.com.dontar.demo.validation.annotation;

import ar.com.dontar.demo.validation.validator.DniValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DniValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDniLong {
    String message() default "La dni debe contar con 8 números";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
