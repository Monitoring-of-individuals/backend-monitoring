package ru.monitoring.util;

import java.util.regex.Pattern;

/**
 * Поскольку в классе введено вынужденное ограничение на используемые символы,
 * то потребуется проверка входящих данных пользователя на соответствие пароля
 * условиям регулярного выражения (regex).
 * <blockquote><pre>
 *     {@code Pattern str = Pattern.compile("[a-zA-Z0-9\\p{Punct}\\s]+");}
 *     {@code Pattern str = Pattern.compile("[\\p{Lower}\\p{Upper}\\p{Digit}\\p{Punct}\\s]+");}
 * </pre></blockquote>
 * Подразумеваются символы английского алфавита в верхнем и нижнем регистре, цифры от 0 до 9,
 * знаки пунктуации: !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~, пробел.
 * Сам класс отвечает только за кодирование / раскодирование пароля, за проверку соответствия символов
 * класс не отвечает. В случае передачи пароля содержащего иные символы после расшифровки пароль не будет
 * соответствовать оригиналу.
 * Проверку входящих данных предлагается делать аннотациями в соответствующем контроллере.
 */

public class Coder {

    public static final String SIMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public String encode(String password, int offset) {
        StringBuilder result = new StringBuilder();
        for (char character : password.toCharArray()) {
            int originalPosition = SIMBOLS.indexOf(character);
            int newPosition = (offset + originalPosition) % 95;
            char newCharacter = SIMBOLS.charAt(newPosition);
            result.append(newCharacter);
        }
        return result.toString();
    }

    public String decode(String password, int offset) {
        return encode(password, 95 - (offset % 95));
    }

    //TODO в случае согласования - перенести в тесты
    public static void main(String[] args) {
        OffsetLoader offsetLoader = OffsetLoader.getInstance();
        int offset = offsetLoader.getOffset();
        Coder coder = new Coder();
        String correctPassword = "Anypassword_ \"!19......\\&][?";
        System.out.println("Правильно написанный пароль: " + correctPassword);

        // Тестируем кодер
        String code = coder.encode(correctPassword, offset);
        System.out.println("Зашифрованный пароль: " + code);
        String decode = coder.decode(code, offset);
        System.out.println("Расшифрованный паоль: " + decode);

        String wrongPassword = "Anypassword_ \"!19..\u018F\u019F....\\&][?";

        // Тестируем паттерн
        Pattern str = Pattern.compile("[a-zA-Z0-9\\p{Punct}\\s]+");
        System.out.println("Результат проверки корректного пароля на соответствие символов: " +
                str.matcher(correctPassword).matches());
        System.out.println("Результат проверки некорректного пароля на соответствие символов: " +
                str.matcher(wrongPassword).matches());
    }
}
