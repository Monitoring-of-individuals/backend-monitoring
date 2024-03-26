package ru.monitoring.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameSurnameValidator implements ConstraintValidator<NameSurname, String> {

    @Override
    public void initialize(NameSurname constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < 1 || value.length() > 32) { // Длина от 1 до 32 символов.
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Имя и Фамилия должны содержать от 1 до 32 символов")
                    .addConstraintViolation();
            return false;
        }

        // Проверка на русские или английские буквы и отсутствие смешивания алфавитов
        if (!value.matches("^[а-яА-ЯёЁa-zA-Z]+$") || value.matches(".*[а-яА-ЯёЁ].*[a-zA-Z].*")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Имя и Фамилия должны содержать только русские или английские " +
                    "буквы без смешивания алфавитов").addConstraintViolation();
            return false;
        }

        // Проверка на пробелы и знаки дефиса
        if (!value.matches("^[\\s\\-]+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Имя и Фамилия должны содержать только пробелы или знаки " +
                    "дефиса").addConstraintViolation();
            return false;
        }

        return true;
    }
}