package ru.monitoring.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext context) {
        if (password == null || password.length() < 8 || password.length() > 32) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Пароль должен содержать от 8 до 32 символов")
                    .addConstraintViolation();
            return false;
        }

        boolean containsUppercase = false;
        boolean containsLowercase = false;
        boolean containsDigit = false;
        boolean containsSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(c)) {
                containsLowercase = true;
            } else if (Character.isDigit(c)) {
                containsDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                containsSpecialChar = true;
            }
        }

        if (!containsUppercase || !containsLowercase || !containsDigit || !containsSpecialChar) {
            context.disableDefaultConstraintViolation();
            if (!containsUppercase) {
                context.buildConstraintViolationWithTemplate(
                                "Пароль должен содержать хотя бы одну заглавную букву")
                        .addConstraintViolation();
            }
            if (!containsLowercase) {
                context.buildConstraintViolationWithTemplate(
                                "Пароль должен содержать хотя бы одну строчную букву")
                        .addConstraintViolation();
            }
            if (!containsDigit) {
                context.buildConstraintViolationWithTemplate(
                                "Пароль должен содержать хотя бы одну цифру")
                        .addConstraintViolation();
            }
            if (!containsSpecialChar) {
                context.buildConstraintViolationWithTemplate(
                                "Пароль должен содержать хотя бы один специальный символ")
                        .addConstraintViolation();
            }
            return false;
        }

        return true;
    }
}
