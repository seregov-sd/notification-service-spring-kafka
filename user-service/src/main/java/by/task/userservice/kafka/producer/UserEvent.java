package by.task.userservice.kafka.producer;

public class UserEvent {
    private UserEventType eventType;
    private String email;

    public UserEvent() {
    }

    public UserEvent(UserEventType eventType, String email) {
        this.eventType = eventType;
        this.email = email;
    }

    public UserEventType getEventType() {
        return eventType;
    }

    public void setEventType(UserEventType eventType) {
        this.eventType = eventType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}