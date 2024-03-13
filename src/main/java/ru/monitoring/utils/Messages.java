package ru.monitoring.utils;

public class Messages {

    public static final String LAST_NAME_MESSAGES = "Параметр \"lastName\" не может быть пустым.";
    public static final String LAST_NAME_REGEXP_MESSAGE = "Переменная\"lastName\" содержит недопустимые символы " +
            "или не соответствует требованиям. Разрешено: кириллица и знак \"-\"";
    public static final String FIRST_NAME_MESSAGES = "Параметр \"firstName\" не может быть пустым.";
    public static final String FIRST_NAME_REGEXP_MESSAGE = "Переменная \"firstName\" содержит недопустимые символы " +
            "или не соответствует требованиям. Разрешено: кириллица и знак \"-\"";
    public static final String PASSPORT_MESSAGE = "В параметре \"passport\" не должно быть больше или меньше 10 цифр";
    public static final String PASSPORT_REGEXP_MESSAGE = "Переменная \"passport\" содержит недопустимые символы " +
            "или не соответствует требованиям. Разрешено: цифры без пробелов";
    public static final String DRIVER_MESSAGE = "В параметре \"drivingLicence\" не должно быть " +
            "больше или меньше 10 цифр. Номер и серия пишутся в одну строчку без пробела.";
    public static final String DRIVER_REGEXP_MESSAGE = "Переменная \"drivingLicence\" содержит недопустимые символы " +
            "или не соответствует требованиям. Разрешено: цифры без пробелов";
}