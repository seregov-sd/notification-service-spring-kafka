package by.task.userservice.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventProducer {

    @Value("${spring.kafka.topic.user-events}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreatedEvent(String email) {
        UserEvent event = new UserEvent(UserEventType.CREATE, email);
        kafkaTemplate.send(topic, event);
    }

    public void sendUserDeletedEvent(String email) {
        UserEvent event = new UserEvent(UserEventType.DELETE, email);
        kafkaTemplate.send(topic, event);
    }
}