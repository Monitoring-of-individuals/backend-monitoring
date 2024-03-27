package ru.monitoring.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameSurnameValidator.class)
@Documented
public @interface NameSurname {
    String message() default "Имя или фамилия не соответствуют требованиям";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}