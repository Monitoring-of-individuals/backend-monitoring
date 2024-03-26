package ru.monitoring.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Пароль не соответствует требованиям";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}