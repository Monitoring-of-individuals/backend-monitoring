package ru.monitoring.service.mail;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для отправки сообщений на почту
 */
public interface IMailService {
    CompletableFuture<Void> sendSuccessfulRegistrationMail(String from,
                                                           String to,
                                                           String userName);

    CompletableFuture<Void> sendConfirmationCode(String from,
                                                 String to,
                                                 String subject,
                                                 String userName,
                                                 String code);
}
