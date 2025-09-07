package by.seregov.notification.service;

import by.seregov.notification.model.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserCreatedEmail(String email) {
        sendEmail(email, EmailTemplate.USER_CREATED);
    }

    public void sendUserDeletedEmail(String email) {
        sendEmail(email, EmailTemplate.USER_DELETED);
    }

    public void sendEmail(String to, EmailTemplate template) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(template.getSubject());
        message.setText(template.getText());
        mailSender.send(message);
    }

    // Для прямых вызовов через API
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}