package com.VU.PSKProject.Utils;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class DateUtils {

    private DateUtils(){
        throw new IllegalStateException("Util class");
    }

    public static LocalDateTime stringsToDate(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
    }

    public static String getLastDayOfMonth(String year, String month) {
        return YearMonth.of(Integer.parseInt(year) , Integer.parseInt(month))
                .atEndOfMonth()
                .format(DateTimeFormatter.ofPattern("d"));
    }
}
