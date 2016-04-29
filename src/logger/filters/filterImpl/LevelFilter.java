package logger.filters.filterImpl;

import logger.Level;
import logger.Message;
import logger.filters.Filter;

public class LevelFilter  implements Filter{
    public LevelFilter(Level min, Level max) {
        setMax(max);
        setMin(min);
    }

    public LevelFilter(String min, String max){
        this(Level.valueOf(min), Level.valueOf(max));
    }

    public Level getMin() {
        return min;
    }

    public void setMin(Level min) {
        this.min = min;
    }

    public Level getMax() {
        return max;
    }

    public void setMax(Level max) {
        this.max = max;
    }

    private Level min, max;

    @Override
    public boolean check(Message msg) {
        if (min == null || min.compareTo(msg.getSeverity()) <= 0)
            if (max == null || max.compareTo(msg.getSeverity()) >= 0)
                return  true;

        return false;
    }
}
