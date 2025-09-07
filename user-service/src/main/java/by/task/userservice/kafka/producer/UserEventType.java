package by.task.userservice.kafka.producer;

public enum UserEventType {
    CREATE("CREATE", "User created successfully", "notification-group"),
    DELETE("DELETE", "User deleted successfully", "notification-group");

    private final String code;
    private final String message;
    private final String consumerGroup;

    UserEventType(String code, String message, String consumerGroup) {
        this.code = code;
        this.message = message;
        this.consumerGroup = consumerGroup;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public static UserEventType fromCode(String code) {
        for (UserEventType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + code);
    }
}