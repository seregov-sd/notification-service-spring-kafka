package by.seregov.notification.model;

public enum EmailTemplate {
    USER_CREATED(
            "Аккаунт успешно создан!",
            "Здравствуйте! Ваш аккаунт в User Service был успешно создан."
    ),
    USER_DELETED(
            "Аккаунт удален!",
            "Здравствуйте! Ваш аккаунт User Service был удалён."
    );

    private final String subject;
    private final String text;

    EmailTemplate(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}