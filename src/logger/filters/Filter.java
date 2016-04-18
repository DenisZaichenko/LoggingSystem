package logger.filters;

import logger.Message;

public interface Filter {
    boolean check(Message msg);
        }
