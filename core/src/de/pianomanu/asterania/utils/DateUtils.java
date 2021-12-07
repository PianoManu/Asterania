package de.pianomanu.asterania.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String calcDate(long milliseconds) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MMM.yyyy-HH:mm");
        Date resultdate = new Date(milliseconds);
        return date_format.format(resultdate);
    }

    public static String calcDate() {
        return calcDate(System.currentTimeMillis());
    }

    public static String milliToHour(long milliseconds) {
        long hours = milliseconds / (60 * 60 * 1000);
        milliseconds %= (60 * 60 * 1000);
        long minutes = milliseconds / (60 * 1000);
        milliseconds %= (60 * 1000);
        long seconds = milliseconds / 1000;
        //return seconds+"";
        String h = hours + "";
        String m = minutes + "";
        String s = seconds + "";
        if (seconds < 10) {
            s = "0" + seconds;
        }
        if (minutes < 10)
            m = "0" + minutes;
        if (hours < 100)
            if (hours < 10)
                h = "00" + hours;
            else
                h = "0" + hours;
        return h + ":" + m + ":" + s;
    }
}
