package ru.monitoring.exceptions;

public class ServerError5xxException extends RuntimeException {
    public ServerError5xxException(String message) {
        super(message);
        System.out.println("СРАБОТАЛО ИСКЛЮЧЕНИЕ");
    }
}
