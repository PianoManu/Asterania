package de.pianomanu.asterania.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MMM.yyyy-HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

    public static String calcDate() {
        return calcDate(System.currentTimeMillis());
    }
}
