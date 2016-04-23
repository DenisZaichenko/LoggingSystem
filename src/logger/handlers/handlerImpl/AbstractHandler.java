package logger.handlers.handlerImpl;

import logger.Message;
import logger.handlers.Handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class AbstractHandler implements Handler{
    public enum Tag {
        DATE("%date"),
        SEVERITY("%severity"),
        CONTENT("%content"),
        SENDER("%sender"),
        EXCEPTION("%exception");

        private String tag;
        Tag(String tag){
            this.tag = tag;
        }

        @Override
        public String toString() {
            return tag;
        }
    }

    private String format;

    public AbstractHandler(String format) {
        setFormat(format);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (format == null)
            throw new IllegalArgumentException("Format can not be null");
        this.format = format;
    }


    @Override
    public abstract void write(Message msg);

    protected String format(Message msg){
        String result = format.replace(Tag.DATE.toString(), new SimpleDateFormat().format(msg.getDate()));
        result = result.replace(Tag.SEVERITY.toString(),    msg.getSeverity().toString());
        result = result.replace(Tag.CONTENT.toString(),     msg.getContent());
        result = result.replace(Tag.SENDER.toString(),      msg.getSender().getName());
        String ex = msg.getException() != null ? msg.getException().toString() : "";
        result = result.replace(Tag.EXCEPTION.toString(), ex);

        return result;
    }
}
