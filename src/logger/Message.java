package logger;

import java.util.Date;

public class Message {
    private Level severity;
    private Date date;
    private String content;
    private Throwable exception;
    private Logger sender;

    Message(Level severity, String content, Throwable exception, Logger sender) {
        if (severity == null || content == null || sender == null)
            throw new IllegalArgumentException("Severity and content can not be null");
        this.date = new Date();
        this.severity = severity;
        this.content = content;
        this.exception = exception;
        this.sender = sender;
    }

    Message(Level severity, String content, Logger sender) {
        this(severity, content, null, sender);
    }

    public Logger getSender() {
        return sender;
    }

    public Level getSeverity() {
        return severity;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Throwable getException() {
        return exception;
    }
}
