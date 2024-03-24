package ru.monitoring.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.monitoring.service.IMailService;

/**
 * Сервис для отправки электронной почты.
 * Использует {@link JavaMailSender} для отправки сообщений.
 */
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;

    /**
     * Отправляет электронное письмо.
     *
     * @param from    Электронный адрес отправителя.
     * @param to      Электронный адрес получателя.
     * @param subject Тема письма.
     * @param text    Текст письма.
     */
    @Override
    public void sendMail(String from,
                         String to,
                         String subject,
                         String htmlContent) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true означает, что письмо будет в формате HTML
        } catch (MessagingException e) {
            // Логика обработки ошибок
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }
}
