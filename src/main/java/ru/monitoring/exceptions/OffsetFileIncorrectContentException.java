package ru.monitoring.exceptions;

public class OffsetFileIncorrectContentException extends RuntimeException{

    public OffsetFileIncorrectContentException(String message) {
        super(message);
    }
}
