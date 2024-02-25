package ru.monitoring.util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ru.monitoring.exceptions.OffsetFileIncorrectContentException;
import ru.monitoring.exceptions.ReadOffsetFileException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class OffsetLoader {

    private String path = "src/main/resources/offset.csv";

    @Getter
    private final int offset;

    private OffsetLoader() {
        this.offset = readOffset();
    }

    @Getter(lazy = true)
    private final static OffsetLoader instance = new OffsetLoader();

    private int readOffset() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            if (StringUtils.isBlank(line)) {
                throw new OffsetFileIncorrectContentException("В файле со значением смещения для шифрования значение отсутствует");
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new OffsetFileIncorrectContentException("В файле со значением смещения записана не цифирное значение");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ошибка! Файл не найден!", e);
        } catch (IOException e) {
            throw new ReadOffsetFileException("Произошла ошибка чтения из файла, возможно файл поврежден", e);
        }
    }

}
