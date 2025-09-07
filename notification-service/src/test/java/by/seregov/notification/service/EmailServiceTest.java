package by.seregov.notification.service;

import by.task.userservice.kafka.producer.UserEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockitoBean
    private JavaMailSender mailSender;

    private static final String EMAIL = "user@test.com";

    @Test
    void sendUserCreatedEmail_ShouldSendEmail() {
        emailService.sendUserCreatedEmail(EMAIL);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendUserDeletedEmail_ShouldSendEmail() {
        emailService.sendUserDeletedEmail(EMAIL);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}