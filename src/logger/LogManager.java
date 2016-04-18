package logger;

import logger.config.ConfigReader;
import logger.filters.Filter;
import logger.handlers.Handler;

import java.util.HashMap;
import java.util.Map;

public class LogManager {
    public final static LogManager instance = new LogManager();
    private final Logger root;
    private final Map<String, Logger> loggers;
    private final Map<String, Handler> handlers;
    private final Map<String, Filter> filters;

    private LogManager () {
        loggers = new HashMap<String, Logger>();
        handlers = new HashMap<String, Handler>();
        filters = new HashMap<String, Filter>();
        root = new Logger("root", null);
        loggers.put("root", root);
        ConfigReader.load("config.xml", this);
    }

    public Logger getLogger(String name){
        Logger logger = loggers.get(name);
        if (logger != null) return logger;
        else return createLogger(name);
    }

    public Logger getRootLogger(){
        return root;
    }

    public Handler getHandler(String name){
        return handlers.get(name);
    }

    public Filter getFilter(String name){
        return filters.get(name);
    }

    public void addHandler(String name, Handler handler) {
        handlers.put(name, handler);
    }

    public void addFilter(String name, Filter filter) {
        filters.put(name, filter);
    }

    private Logger createLogger(String name){
        if (!isCorrectName(name))
            throw new IllegalArgumentException("This name is incorrect");

        Logger parent;
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0) {
            String parentName = name.substring(0, lastDot);
            parent = getLogger(parentName);
        }
        else parent = root;

        Logger logger = new Logger(name, parent);
        loggers.put(name, logger);
        return logger;
    }

    private static boolean isCorrectName(String name){
        if (name == null || name.isEmpty()) return false;
        if (name.startsWith(".") || name.endsWith(".")) return false;
        if (name.contains("..")) return false;

        return true;
    }
}
