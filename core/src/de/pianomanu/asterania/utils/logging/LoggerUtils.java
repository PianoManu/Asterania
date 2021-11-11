package de.pianomanu.asterania.utils.logging;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtils {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

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

        logger.setLevel(Level.INFO);
        /*fileTxt = new FileHandler("Logging.txt");
        fileHTML = new FileHandler("Logging.html");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create an HTML formatter
        formatterHTML = new HtmlLogFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);*/
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        consoleHTML = new ConsoleHandler();
        formatterConsole = new ConsoleLogFormatter();
        consoleHTML.setFormatter(formatterConsole);
        logger.addHandler(consoleHTML);

    }
}
