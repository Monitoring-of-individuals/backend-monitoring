package ru.monitoring.exceptions;

public class ApiCloudResponseException extends RuntimeException {
    public ApiCloudResponseException(String message) {
        super(message);
    }
}
