package logger;

import logger.filters.Filter;
import logger.handlers.Handler;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private final String name;
    private final Logger parent;
    private final List<Handler> handlers;
    private Filter filter;

    Logger(String name, Logger parent){
        handlers = new ArrayList<Handler>();
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void addHandler(Handler handler){
        handlers.add(handler);
    }

    public void removeHandler(Handler handler) {
        handlers.remove(handler);
    }

    public Logger getParent() {
        return parent;
    }

    private void log(Message msg){
        if (filter == null || filter.check(msg)){
            if (parent != null)
                parent.log(msg);

            for (Handler h : handlers)
                h.write(msg);
        }
    }

    public void log(Level lvl, String content, Throwable ex){
        Message msg = new Message(lvl, content, ex, this);
        log(msg);
    }

    public void log(Level lvl, String content){
        Message msg = new Message(lvl, content, this);
        log(msg);
    }

    public void debug   (String content) { log(Level.DEBUG,  content); }
    public void info    (String content) { log(Level.INFO,   content); }
    public void warning (String content) { log(Level.WARN,content); }
    public void error   (String content) { log(Level.ERROR,  content); }
    public void fatal   (String content) { log(Level.FATAL,  content); }
    public void error   (String content, Throwable ex) { log(Level.ERROR,  content, ex); }
    public void fatal   (String content, Throwable ex) { log(Level.FATAL,  content, ex); }
}
