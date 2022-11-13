package model;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Logging {
    private static Logger logger = null;

    public static void log(Object caller, String msg) throws IOException {
        logger = Logger.getLogger(caller.getClass().getName());
        logger.setUseParentHandlers(false);
        FileHandler fh;

        try {
            fh = new FileHandler("app.log", true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(caller.getClass().getName().toUpperCase() + " | " + format,
                            new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "\n\t>>"+msg);
    }
}
