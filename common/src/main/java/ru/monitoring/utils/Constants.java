package ru.monitoring.utils;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final int TIMEOUT = 150000; // ТАЙМАУТ СОЕДИНЕНИЯ

    public static final String PATTERN_DATE = "dd.MM.yyyy";

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String API_CLOUD_TOKEN = "2ba194395a7000681938deaaddb6586c";

    public static String API_CLOUD_URL = "http://api-cloud.ru";
}