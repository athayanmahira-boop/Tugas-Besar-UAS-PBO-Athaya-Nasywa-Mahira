package util;

import java.sql.Date;
import java.time.LocalDate;

// Utility konversi LocalDate -> java.sql.Date
public class DateUtil {
    public static Date toSqlDate(LocalDate date) {
        return Date.valueOf(date);
    }
}

