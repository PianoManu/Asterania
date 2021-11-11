package de.pianomanu.asterania.utils.logging;

import de.pianomanu.asterania.AsteraniaMain;

import java.text.SimpleDateFormat;
import java.util.Date;
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
            formattedText += ANSI_RED_BOLD + ANSI_BLACK_BACKGROUND + "[==SEVERE==] ";
        }
        if (record.getLevel().intValue() == Level.WARNING.intValue()) {
            formattedText += ANSI_YELLOW + "[===WARN===] ";
        }
        if (record.getLevel().intValue() == Level.INFO.intValue()) {
            formattedText += ANSI_GREEN + "[===INFO===] ";
        }
        if (record.getLevel().intValue() <= Level.FINE.intValue()) {
            formattedText += ANSI_BLUE + "[===DEBUG==] ";
        }

        formattedText += "(" + calcDate(System.currentTimeMillis()) + ") ";
        formattedText += record.getSourceClassName() + "::" + record.getSourceMethodName() + ": ";
        formattedText += formatMessage(record) + ANSI_RESET + "\n";
        return formattedText;
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format;
        if (AsteraniaMain.LOG_LEVEL == Level.FINER || AsteraniaMain.LOG_LEVEL == Level.FINEST)
            date_format = new SimpleDateFormat("dd.MM.yy HH:mm:ss.SSS");
        else
            date_format = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}
