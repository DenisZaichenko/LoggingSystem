package logger.handlers;

import logger.Message;

public interface Handler {
    void write(Message msg);
}
