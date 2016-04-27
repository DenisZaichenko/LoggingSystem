package logger.handlers.handlerImpl;

import logger.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConsoleHandler  extends AbstractHandler{
    public ConsoleHandler(String format, DateFormat dateFormat) {
        super(format);
    }

    public ConsoleHandler(String format) {
        super(format);
    }

    public ConsoleHandler() {
        this(String.format("[%s] %s\t%s\t%s\t%s\n", Tag.DATE, Tag.SEVERITY, Tag.SENDER, Tag.CONTENT, Tag.EXCEPTION));
    }
    @Override
    public void write(Message msg) {
        System.out.print(format(msg));
    }
}
