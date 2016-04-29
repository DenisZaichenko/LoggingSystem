package logger.filters.filterImpl;

import logger.Level;
import logger.Message;
import logger.filters.Filter;

public class LevelFilter  implements Filter{
    public LevelFilter(Level min) {
        setMin(min);
    }
    public Level getMin() {
        return min;
    }

    public void setMin(Level min) {
        this.min = min;
    }


    private Level min;

    @Override
    public boolean check(Message msg) {
        if (min == null || min.compareTo(msg.getSeverity()) <= 0)
                return  true;

        return false;
    }
}
