package ru.monitoring.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameSurnameValidator implements ConstraintValidator<NameSurname, String> {

    @Override
    public void initialize(NameSurname constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext context) {
        if (value == null || value.length() < 1
                || value.length() > 32) { // Длина от 1 до 32 символов.
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Имя и Фамилия должны содержать от 1 до 32 символов")
                    .addConstraintViolation();
            return false;
        }

        // Комбинированная проверка: Содержит ли строка буквы одного алфавита и допустимые пробелы или дефисы между словами.
        // Также проверяется, что не происходит смешивание алфавитов в одной строке.
        boolean isRussian = value.matches("^[а-яА-ЯёЁ]+([-\\s]?[а-яА-ЯёЁ]+)*$");
        boolean isEnglish = value.matches("^[a-zA-Z]+([-\\s]?[a-zA-Z]+)*$");

        if (!isRussian && !isEnglish) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Имя и Фамилия должны содержать только русские или английские буквы, пробелы или знаки дефиса, "
                                    + "при этом слова должны быть написаны в одном языке, без смешивания алфавитов")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}