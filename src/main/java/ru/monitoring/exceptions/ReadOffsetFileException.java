package ru.monitoring.exceptions;

import java.io.IOException;

public class ReadOffsetFileException extends RuntimeException{

    public ReadOffsetFileException(String message, IOException cause) {
        super(message, cause);
    }
}
