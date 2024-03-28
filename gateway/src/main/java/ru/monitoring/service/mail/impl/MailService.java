package ru.monitoring.service.mail.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.monitoring.service.mail.IMailService;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для отправки электронной почты.
 * Использует {@link JavaMailSender} для отправки сообщений.
 */
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private static final String REG_CODE_TEMPLATE_HTML = "registration_code_email_template.html";
    private static final String REG_SUCCESS_TEMPLATE_HTML = "registration_done_email_template.html";
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     * Отправляет электронное письмо об успешной регистрации.
     *
     * @param from     Электронный адрес отправителя.
     * @param to       Электронный адрес получателя.
     * @param userName Текст письма.
     */
    @Override
    @Async
    public CompletableFuture<Void> sendSuccessfulRegistrationMail(String from,
                                                                  String to,
                                                                  String userName) {
        return CompletableFuture.runAsync(() -> {
            Context context = new Context();
            context.setVariable("username", userName);
            String message = templateEngine.process(REG_SUCCESS_TEMPLATE_HTML, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject("Успешная регистрация");
                helper.setText(message);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new MailSendException(e.getMessage(), e.getCause());
            }
        });
        // TODO добавить либо handle либо exceptionally и логирование в  случае ошибок
    }

    /**
     * Отправляет электронное письмо с кодом подтверждения.
     *
     * @param from     Электронный адрес отправителя.
     * @param to       Электронный адрес получателя.
     * @param subject  Тема письма.
     * @param userName Текст письма.
     * @param code     6 значный код подтверждения.
     */
    @Override
    @Async
    public CompletableFuture<Void> sendConfirmationCode(String from,
                                                        String to,
                                                        String subject,
                                                        String userName,
                                                        String code) {
        return CompletableFuture.runAsync(() -> {
            Context context = new Context();
            context.setVariable("username", userName);
            context.setVariable("code", code);
            String message = templateEngine.process(REG_CODE_TEMPLATE_HTML, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(message);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new MailSendException(e.getMessage(), e.getCause());
            }
        });
        // TODO добавить либо handle либо exceptionally и логирование в  случае ошибок
    }
}
