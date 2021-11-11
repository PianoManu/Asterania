package de.pianomanu.asterania.utils.logging;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BOLD = "\033[1;31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    @Override
    public String format(LogRecord record) {
        String formattedText = "";
        if (record.getLevel().intValue() == Level.SEVERE.intValue()) {
            formattedText += ANSI_RED_BOLD + ANSI_BLACK_BACKGROUND;
        }
        if (record.getLevel().intValue() == Level.WARNING.intValue()) {
            formattedText += ANSI_YELLOW;
        }
        if (record.getLevel().intValue() == Level.INFO.intValue()) {
            formattedText += ANSI_GREEN;
        }
        if (record.getLevel().intValue() <= Level.FINE.intValue()) {
            formattedText += ANSI_BLUE;
        }

        formattedText += record.getSourceClassName() + "#" + record.getSourceMethodName() + ": ";
        formattedText += formatMessage(record) + ANSI_RESET + "\n";
        return formattedText;
    }
}
