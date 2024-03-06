package ru.monitoring.exceptions;

public class ClentError4xxException extends RuntimeException {
    public ClentError4xxException(String message) {
        super(message);
    }
}
