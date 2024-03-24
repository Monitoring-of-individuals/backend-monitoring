package ru.monitoring.service;

/**
 * Сервис для отправки сообщений на почту
 */
public interface IMailService {
    void sendMail(String from,
                  String to,
                  String subject,
                  String text);
}
