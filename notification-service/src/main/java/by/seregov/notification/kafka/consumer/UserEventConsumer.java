package by.seregov.notification.kafka.consumer;

import by.seregov.notification.service.EmailService;
import by.task.userservice.kafka.producer.UserEvent;
import by.task.userservice.kafka.producer.UserEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UserEventConsumer.class);

    private final EmailService emailService;

    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(UserEvent event) {
        logger.info("Received UserEvent: {}", event);

        if (event.getEventType() == UserEventType.CREATE) {
            emailService.sendUserCreatedEmail(event.getEmail());
            logger.info("Sent user created email to: {}", event.getEmail());
        } else if (event.getEventType() == UserEventType.DELETE) {
            emailService.sendUserDeletedEmail(event.getEmail());
            logger.info("Sent user deleted email to: {}", event.getEmail());
        }
    }
}