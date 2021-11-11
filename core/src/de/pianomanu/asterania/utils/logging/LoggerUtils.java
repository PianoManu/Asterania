package de.pianomanu.asterania.utils.logging;

import de.pianomanu.asterania.AsteraniaMain;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class LoggerUtils {
    static private ConsoleHandler consoleHTML;
    static private Formatter formatterConsole;

    public static void initializeLogger() {
        try {
            setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setup() throws IOException {
        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(AsteraniaMain.LOG_LEVEL);
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        consoleHTML = new ConsoleHandler();
        formatterConsole = new ConsoleLogFormatter();
        consoleHTML.setLevel(AsteraniaMain.LOG_LEVEL);
        consoleHTML.setFormatter(formatterConsole);
        logger.addHandler(consoleHTML);

    }
}
